package com.vipshop.microscope.trace.stoarge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.microscope.common.logentry.Codec;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.common.system.SystemInfo;
import com.vipshop.microscope.common.thrift.ThriftCategory;
import com.vipshop.microscope.common.thrift.ThriftClient;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Store message in client use {@code Disruptor}.
 *
 * @author Xu Fei
 * @version 1.0
 */

public class DisruptorQueueStorage implements Storage {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorQueueStorage.class);

    /**
     * LogEntry Event.
     *
     * @author Xu Fei
     * @version 1.0
     */
    static class LogEntryEvent {
        public Object logEntry;

        public Object getLogEntry() {
            return logEntry;
        }

        public void setLogEntry(Object logEntry) {
            this.logEntry = logEntry;
        }

        public final static EventFactory<LogEntryEvent> EVENT_FACTORY = new EventFactory<LogEntryEvent>() {
            public LogEntryEvent newInstance() {
                return new LogEntryEvent();
            }
        };
    }

    /**
     * LogEntry send processor.
     *
     * @author Xu Fei
     * @version 1.0
     */
    static class LogEntryEventHandler implements EventHandler<LogEntryEvent> {

        /**
         * use thrift client send {@code LogEntry}
         */
        private final ThriftClient client = new ThriftClient(Tracer.COLLECTOR_HOST,
                                                             Tracer.COLLECTOR_PORT,
                                                             Tracer.RECONNECT_WAIT_TIME,
                                                             ThriftCategory.THREAD_SELECTOR);

        private final int MAX_BATCH_SIZE = Tracer.MAX_BATCH_SIZE;
        private final int MAX_EMPTY_SIZE = Tracer.MAX_EMPTY_SIZE;

        /**
         * use for batch send
         */
        private final List<LogEntry> logEntries = new ArrayList<LogEntry>(MAX_BATCH_SIZE);

        private int emptySize = 0;

        @Override
        public void onEvent(LogEntryEvent event, long sequence, boolean endOfBatch) throws Exception {
            Object object = event.getLogEntry();

            LogEntry logEntry = poll(object);

            if (logEntry == null)
                emptySize++;
            else {
                logEntries.add(logEntry);
            }

            boolean emptySizeFlag = emptySize >= MAX_EMPTY_SIZE && !logEntries.isEmpty();
            boolean batchSizeFlag = logEntries.size() >= MAX_BATCH_SIZE;

            if (emptySizeFlag || batchSizeFlag) {
                client.send(logEntries);
                logEntries.clear();
                emptySize = 0;
            } else {
                try {
                    TimeUnit.MICROSECONDS.sleep(Tracer.SEND_WAIT_TIME);
                } catch (InterruptedException e) {
                    logger.debug("Ignore Thread Interrupted exception", e);
                }
            }
        }

        private LogEntry poll(Object object) {

            /**
             * construct trace LogEntry
             */
            if (object instanceof Span) {
                LogEntry logEntry = null;
                try {
                    logEntry = Codec.encodeToLogEntry((Span) object);
                } catch (Exception e) {
                    logger.debug("encode span to logEntry error", e);
                    return null;
                }
                return logEntry;
            }

            /**
             * construct metric LogEntry
             */
            if (object instanceof Metric) {
                LogEntry logEntry = null;
                try {
                    logEntry = Codec.encodeToLogEntry((Metric) object);
                } catch (Exception e) {
                    logger.debug("encode metric to logEntry error", e);
                    return null;
                }
                return logEntry;
            }

            /**
             * construct exception LogEntry
             */
            if (object instanceof HashMap) {
                LogEntry logEntry = null;
                try {
                    logEntry = Codec.encodeToLogEntry((HashMap<String, Object>)object);
                } catch (Exception e) {
                    logger.debug("encode exception to logEntry error", e);
                    return null;
                }
                return logEntry;
            }

            /**
             * construct system info LogEntry
             */
            if (object instanceof SystemInfo) {
                LogEntry logEntry = null;
                try {
                    logEntry = Codec.encodeToLogEntry((SystemInfo)object);
                } catch (Exception e) {
                    logger.debug("encode system info to logEntry error", e);
                    return null;
                }
                return logEntry;
            }

            return null;
        }

    }

    private final int LOGENTRY_BUFFER_SIZE = 1024 * 8 * 1 * 1;

    private static volatile boolean start = false;

    /**
     * LogEntry RingBuffer
     */
    private final RingBuffer<LogEntryEvent> logEntryRingBuffer;
    private final SequenceBarrier logEntrySequenceBarrier;
    private final BatchEventProcessor<LogEntryEvent> logEntryEventProcessor;

    public DisruptorQueueStorage() {

        this.logEntryRingBuffer = RingBuffer.createMultiProducer(LogEntryEvent.EVENT_FACTORY, LOGENTRY_BUFFER_SIZE, new SleepingWaitStrategy());
        this.logEntrySequenceBarrier = logEntryRingBuffer.newBarrier();
        this.logEntryEventProcessor = new BatchEventProcessor<LogEntryEvent>(logEntryRingBuffer, logEntrySequenceBarrier, new LogEntryEventHandler());

        logger.info("start disruptor transporter thread send LogEntry");

        ExecutorService logEntryExecutor = ThreadPoolUtil.newSingleDaemonThreadExecutor("disruptor-transporter");
        logEntryExecutor.execute(this.logEntryEventProcessor);

        start = true;
    }

    public LogEntry poll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void addMetrics(Metric metrics) {
        publish(metrics);
    }

    @Override
    public void addSpan(Span span) {
        publish(span);
    }

    @Override
    public void addException(Map<String, Object> exceptionInfo) {
        publish(exceptionInfo);
    }

    @Override
    public void addSystemInfo(SystemInfo system) {
        publish(system);
    }

    private void publish(Object object) {
        if (start && object != null) {
            long sequence = this.logEntryRingBuffer.next();
            this.logEntryRingBuffer.get(sequence).setLogEntry(object);
            this.logEntryRingBuffer.publish(sequence);
        }
    }


}
