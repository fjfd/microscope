package com.vipshop.microscope.trace.storage;

import com.lmax.disruptor.*;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.thrift.ThriftCategory;
import com.vipshop.microscope.thrift.ThriftClient;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.codec.Codec;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.sample.Sampler;
import com.vipshop.microscope.trace.sample.SamplerHolder;
import com.vipshop.microscope.trace.system.SystemData;
import com.vipshop.microscope.trace.transport.Transporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Store message in client use {@code Disruptor}.
 *
 * @author Xu Fei
 * @version 1.0
 */

public class DisruptorQueueStorage implements Storage, Transporter {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorQueueStorage.class);

    private static final Sampler SAMPLER = SamplerHolder.getSampler();
    private static volatile boolean start = false;

    private final int LOGENTRY_BUFFER_SIZE = 1024 * 8 * 1 * 1;

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
    }

    @Override
    public void transport() {

        logger.info("start disruptor transporter thread send LogEntry");

        ExecutorService logEntryExecutor = ThreadPoolUtil.newSingleDaemonThreadExecutor("disruptor-transporter");
        logEntryExecutor.execute(this.logEntryEventProcessor);

        start = true;

    }

    public LogEntry poll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(MetricData metrics) {
        publish(metrics);
    }

    @Override
    public void add(Span span) {
        if (SAMPLER.sample(span.getTraceId())) {
            publish(span);
        }
    }

    @Override
    public void add(ExceptionData exception) {
        publish(exception);
    }

    @Override
    public void add(SystemData system) {
        publish(system);
    }

    private void publish(Object object) {
        if (start && object != null) {
            long sequence = this.logEntryRingBuffer.next();
            this.logEntryRingBuffer.get(sequence).setLogEntry(object);
            this.logEntryRingBuffer.publish(sequence);
        }
    }

    /**
     * LogEntry Event.
     *
     * @author Xu Fei
     * @version 1.0
     */
    public static class LogEntryEvent {
        public final static EventFactory<LogEntryEvent> EVENT_FACTORY = new EventFactory<LogEntryEvent>() {
            public LogEntryEvent newInstance() {
                return new LogEntryEvent();
            }
        };
        public Object logEntry;

        public Object getLogEntry() {
            return logEntry;
        }

        public void setLogEntry(Object logEntry) {
            this.logEntry = logEntry;
        }
    }

    /**
     * LogEntry send processor.
     *
     * @author Xu Fei
     * @version 1.0
     */
    public static class LogEntryEventHandler implements EventHandler<LogEntryEvent> {

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
            }
        }

        private LogEntry poll(Object object) {

            /**
             * construct trace LogEntry
             */
            if (object instanceof Span) {
                LogEntry logEntry = null;
                try {
                    logEntry = Codec.toLogEntry((Span) object);
                } catch (Exception e) {
                    logger.debug("encode span to logEntry error", e);
                    return null;
                }
                return logEntry;
            }

            /**
             * construct metric LogEntry
             */
            if (object instanceof MetricData) {
                LogEntry logEntry = null;
                try {
                    logEntry = Codec.toLogEntry((MetricData) object);
                } catch (Exception e) {
                    logger.debug("encode metric to logEntry error", e);
                    return null;
                }
                return logEntry;
            }

            /**
             * construct exception LogEntry
             */
            if (object instanceof ExceptionData) {
                LogEntry logEntry = null;
                try {
                    logEntry = Codec.toLogEntry((ExceptionData) object);
                } catch (Exception e) {
                    logger.debug("encode exception to logEntry error", e);
                    return null;
                }
                return logEntry;
            }

            /**
             * construct system LogEntry
             */
            if (object instanceof SystemData) {
                LogEntry logEntry = null;
                try {
                    logEntry = Codec.toLogEntry((SystemData) object);
                } catch (Exception e) {
                    logger.debug("encode system info to logEntry error", e);
                    return null;
                }
                return logEntry;
            }

            return null;
        }

    }


}
