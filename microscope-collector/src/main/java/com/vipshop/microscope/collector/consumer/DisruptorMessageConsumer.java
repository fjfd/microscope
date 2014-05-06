package com.vipshop.microscope.collector.consumer;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.microscope.collector.disruptor.*;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.thrift.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * A version use {@code Disruptor} to consume message.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class DisruptorMessageConsumer implements MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorMessageConsumer.class);

    private final int LOGENTRY_BUFFER_SIZE = 1024 * 8 * 8 * 4;
    private final int TRACE_BUFFER_SIZE = 1024 * 8 * 8 * 2;
    private final int METRICS_BUFFER_SIZE = 1024 * 8 * 4 * 2;
    private final int EXCEPTION_BUFFER_SIZE = 1024 * 8 * 2 * 1;
    private final int LOG_BUFFER_SIZE = 1024 * 8 * 1 * 1;
    private final int GCLOG_BUFFER_SIZE = 1024 *8 * 1 * 1;

    /**
     * Trace RingBuffer
     */
    private final RingBuffer<TraceEvent> traceRingBuffer;
    private final SequenceBarrier traceSequenceBarrier;
    private final BatchEventProcessor<TraceEvent> traceAlertEventProcessor;
    private final BatchEventProcessor<TraceEvent> traceAnalyzeEventProcessor;
    private final BatchEventProcessor<TraceEvent> traceStorageEventProcessor;

    /**
     * Metric RingBuffer
     */
    private final RingBuffer<MetricEvent> metricRingBuffer;
    private final SequenceBarrier metricSequenceBarrier;
    private final BatchEventProcessor<MetricEvent> metricAlertEventProcessor;
    private final BatchEventProcessor<MetricEvent> metricAnalyzeEventProcessor;
    private final BatchEventProcessor<MetricEvent> metricStorageEventProcessor;

    /**
     * Exception RingBuffer
     */
    private final RingBuffer<ExceptionEvent> exceptionRingBuffer;
    private final SequenceBarrier exceptionSequenceBarrier;
    private final BatchEventProcessor<ExceptionEvent> exceptionAlertEventProcessor;
    private final BatchEventProcessor<ExceptionEvent> exceptionAnalyzeEventProcessor;
    private final BatchEventProcessor<ExceptionEvent> exceptionStorageEventProcessor;

    /**
     * LogEntry RingBuffer
     */
    private final RingBuffer<LogEntryEvent> logEntryRingBuffer;
    private final SequenceBarrier logEntrySequenceBarrier;
    private final BatchEventProcessor<LogEntryEvent> logEntryEventProcessor;

    /**
     * LogEvent RingBuffer
     */
    private final RingBuffer<LogEvent> logRingBuffer;
    private final SequenceBarrier logSequenceBarrier;
    private final BatchEventProcessor<LogEvent> logAlertEventProcessor;
    private final BatchEventProcessor<LogEvent> logAnalyzeEventProcessor;
    private final BatchEventProcessor<LogEvent> logStoreEventProcessor;

    /**
     * GCLogEvent RingBuffer
     */
    private final RingBuffer<GCLogEvent> gcLogRingBuffer;
    private final SequenceBarrier gcLogSequenceBarrier;
    private final BatchEventProcessor<GCLogEvent> gcLogStoreEventProcessor;

    private volatile boolean start = false;

    /**
     * Construct DisruptorMessageConsumer
     */
    public DisruptorMessageConsumer() {

        this.traceRingBuffer = RingBuffer.createSingleProducer(TraceEvent.EVENT_FACTORY, TRACE_BUFFER_SIZE, new SleepingWaitStrategy());
        this.traceSequenceBarrier = traceRingBuffer.newBarrier();
        this.traceAlertEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceAlertHandler());
        this.traceAnalyzeEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceAnalyzeHandler());
        this.traceStorageEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceStorageHandler());
        this.traceRingBuffer.addGatingSequences(traceAlertEventProcessor.getSequence());
        this.traceRingBuffer.addGatingSequences(traceAnalyzeEventProcessor.getSequence());
        this.traceRingBuffer.addGatingSequences(traceStorageEventProcessor.getSequence());

        this.metricRingBuffer = RingBuffer.createSingleProducer(MetricEvent.EVENT_FACTORY, METRICS_BUFFER_SIZE, new SleepingWaitStrategy());
        this.metricSequenceBarrier = metricRingBuffer.newBarrier();
        this.metricAlertEventProcessor = new BatchEventProcessor<MetricEvent>(metricRingBuffer, metricSequenceBarrier, new MetricAlertHandler());
        this.metricAnalyzeEventProcessor = new BatchEventProcessor<MetricEvent>(metricRingBuffer, metricSequenceBarrier, new MetricAnalyzeHandler());
        this.metricStorageEventProcessor = new BatchEventProcessor<MetricEvent>(metricRingBuffer, metricSequenceBarrier, new MetricStorageHandler());
        this.metricRingBuffer.addGatingSequences(metricAlertEventProcessor.getSequence());
        this.metricRingBuffer.addGatingSequences(metricAnalyzeEventProcessor.getSequence());
        this.metricRingBuffer.addGatingSequences(metricStorageEventProcessor.getSequence());

        this.exceptionRingBuffer = RingBuffer.createSingleProducer(ExceptionEvent.EVENT_FACTORY, EXCEPTION_BUFFER_SIZE, new SleepingWaitStrategy());
        this.exceptionSequenceBarrier = exceptionRingBuffer.newBarrier();
        this.exceptionAlertEventProcessor = new BatchEventProcessor<ExceptionEvent>(exceptionRingBuffer, exceptionSequenceBarrier, new ExceptionAlertHandler());
        this.exceptionAnalyzeEventProcessor = new BatchEventProcessor<ExceptionEvent>(exceptionRingBuffer, exceptionSequenceBarrier, new ExceptionAnalyzeHandler());
        this.exceptionStorageEventProcessor = new BatchEventProcessor<ExceptionEvent>(exceptionRingBuffer, exceptionSequenceBarrier, new ExceptionStorageHandler());
        this.exceptionRingBuffer.addGatingSequences(exceptionAlertEventProcessor.getSequence());
        this.exceptionRingBuffer.addGatingSequences(exceptionAnalyzeEventProcessor.getSequence());
        this.exceptionRingBuffer.addGatingSequences(exceptionStorageEventProcessor.getSequence());

        this.logEntryRingBuffer = RingBuffer.createSingleProducer(LogEntryEvent.EVENT_FACTORY, LOGENTRY_BUFFER_SIZE, new SleepingWaitStrategy());
        this.logEntrySequenceBarrier = logEntryRingBuffer.newBarrier();
        this.logEntryEventProcessor = new BatchEventProcessor<LogEntryEvent>(logEntryRingBuffer, logEntrySequenceBarrier,
                                                                                                         new LogEntryHandler(traceRingBuffer,
                                                                                                                             metricRingBuffer,
                                                                                                                             exceptionRingBuffer)
        );

        this.logRingBuffer = RingBuffer.createSingleProducer(LogEvent.EVENT_FACTORY, LOG_BUFFER_SIZE, new SleepingWaitStrategy());
        this.logSequenceBarrier = this.logRingBuffer.newBarrier();
        this.logAlertEventProcessor = new BatchEventProcessor<LogEvent>(logRingBuffer, logSequenceBarrier, new LogAlertHandler());
        this.logAnalyzeEventProcessor = new BatchEventProcessor<LogEvent>(logRingBuffer, logSequenceBarrier, new LogAnalyzeHandler());
        this.logStoreEventProcessor = new BatchEventProcessor<LogEvent>(logRingBuffer, logSequenceBarrier, new LogStorageHandler());
        this.logRingBuffer.addGatingSequences(logAlertEventProcessor.getSequence());
        this.logRingBuffer.addGatingSequences(logAnalyzeEventProcessor.getSequence());
        this.logRingBuffer.addGatingSequences(logStoreEventProcessor.getSequence());

        this.gcLogRingBuffer = RingBuffer.createSingleProducer(GCLogEvent.EVENT_FACTORY, GCLOG_BUFFER_SIZE, new SleepingWaitStrategy());
        this.gcLogSequenceBarrier = gcLogRingBuffer.newBarrier();
        this.gcLogStoreEventProcessor = new BatchEventProcessor<GCLogEvent>(gcLogRingBuffer, gcLogSequenceBarrier, new GCLogStorageHandler());

    }

    /**
     * Start event processors
     */
    @Override
    public void start() {

        logger.info("start message consumer base on disruptor");

        logger.info("start LogEntry process thread");
        ExecutorService logEntryValidateExecutor = ThreadPoolUtil.newSingleThreadExecutor("LogEntry-process-pool");
        logEntryValidateExecutor.execute(this.logEntryEventProcessor);

        logger.info("start trace alert thread");
        ExecutorService traceAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-alert-pool");
        traceAlertExecutor.execute(this.traceAlertEventProcessor);

        logger.info("start trace analyze thread");
        ExecutorService traceAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-analyze-pool");
        traceAnalyzeExecutor.execute(this.traceAnalyzeEventProcessor);

        logger.info("start trace saveLog thread");
        ExecutorService traceStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-saveLog-pool");
        traceStoreExecutor.execute(this.traceStorageEventProcessor);

        logger.info("start metric alert thread");
        ExecutorService metricsAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("metric-alert-pool");
        metricsAlertExecutor.execute(this.metricAlertEventProcessor);

        logger.info("start metric analyze thread");
        ExecutorService metricsAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("metric-analyze-pool");
        metricsAnalyzeExecutor.execute(this.metricAnalyzeEventProcessor);

        logger.info("start metric saveLog thread");
        ExecutorService metricsStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("metric-saveLog-pool");
        metricsStoreExecutor.execute(this.metricStorageEventProcessor);

        logger.info("start exception alert thread");
        ExecutorService exceptionAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("exception-alert-pool");
        exceptionAlertExecutor.execute(this.exceptionAlertEventProcessor);

        logger.info("start exception analyze thread");
        ExecutorService exceptionAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("exception-analyze-pool");
        exceptionAnalyzeExecutor.execute(this.exceptionAnalyzeEventProcessor);

        logger.info("start exception saveLog thread");
        ExecutorService exceptionStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("exception-saveLog-pool");
        exceptionStoreExecutor.execute(this.exceptionStorageEventProcessor);

        logger.info("start log alert thread");
        ExecutorService logAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("log-alert-pool");
        logAlertExecutor.execute(this.logAlertEventProcessor);

        logger.info("start log analyze thread");
        ExecutorService logAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("log-analyze-pool");
        logAnalyzeExecutor.execute(this.logAnalyzeEventProcessor);

        logger.info("start log saveLog thread");
        ExecutorService logStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("log-saveLog-pool");
        logStoreExecutor.execute(this.logStoreEventProcessor);

        logger.info("start gc log saveLog thread");
        ExecutorService gcLogStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("gc-log-saveLog-pool");
        gcLogStoreExecutor.execute(this.gcLogStoreEventProcessor);

        start = true;
    }

    /**
     * Publish LogEntry to LogEntry RingBuffer
     */
    @Override
    public void publish(LogEntry logEntry) {
        if (start && logEntry != null) {
            long sequence = this.logEntryRingBuffer.next();
            this.logEntryRingBuffer.get(sequence).setResult(logEntry);
            this.logEntryRingBuffer.publish(sequence);
        }
    }

    /**
     * Publish logs to consumer.
     *
     * @param log log4j/gc logs
     */
    @Override
    public void publish(String log) {
        if (start && log != null) {
            long sequence = this.logRingBuffer.next();
            this.logRingBuffer.get(sequence).setResult(log);
            this.logRingBuffer.publish(sequence);
        }
    }

    /**
     * Publish gc logs to consumer.
     *
     * @param gcLog gc logs
     */
    @Override
    public void publishGCLog(String gcLog) {
        if (start && gcLog != null) {
            long sequence = this.gcLogRingBuffer.next();
            this.gcLogRingBuffer.get(sequence).setResult(gcLog);
            this.gcLogRingBuffer.publish(sequence);
        }
    }

    /**
     * Stop all event processors.
     */
    @Override
    public void shutdown() {

        /**
         * close LogEntry process thread
         */
        logEntryEventProcessor.halt();

        /**
         * close trace process thread
         */
        traceAlertEventProcessor.halt();
        traceAnalyzeEventProcessor.halt();
        traceStorageEventProcessor.halt();

        /**
         * close metric process thread
         */
        metricAlertEventProcessor.halt();
        metricAnalyzeEventProcessor.halt();
        metricStorageEventProcessor.halt();

        /**
         * close exception process thread
         */
        exceptionAlertEventProcessor.halt();
        exceptionAnalyzeEventProcessor.halt();
        exceptionStorageEventProcessor.halt();

        /**
         * close log process thread
         */
        logAlertEventProcessor.halt();
        logAnalyzeEventProcessor.halt();
        logStoreEventProcessor.halt();

        /**
         * close gc log process thread
         */
        gcLogStoreEventProcessor.halt();

    }

}
