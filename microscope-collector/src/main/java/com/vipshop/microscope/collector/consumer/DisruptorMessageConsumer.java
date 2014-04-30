package com.vipshop.microscope.collector.consumer;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.microscope.collector.disruptor.*;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.gen.LogEntry;
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
    /**
     * Trace RingBuffer
     */
    private final RingBuffer<TraceEvent> traceRingBuffer;
    private final SequenceBarrier traceSequenceBarrier;
    private final BatchEventProcessor<TraceEvent> traceAlertEventProcessor;
    private final BatchEventProcessor<TraceEvent> traceAnalyzeEventProcessor;
    private final BatchEventProcessor<TraceEvent> traceStorageEventProcessor;
    /**
     * Metrics RingBuffer
     */
    private final RingBuffer<MetricsEvent> metricsRingBuffer;
    private final SequenceBarrier metricsSequenceBarrier;
    private final BatchEventProcessor<MetricsEvent> metricsAlertEventProcessor;
    private final BatchEventProcessor<MetricsEvent> metricsAnalyzeEventProcessor;
    private final BatchEventProcessor<MetricsEvent> metricsStorageEventProcessor;
    /**
     * Exception RingBuffer
     */
    private final RingBuffer<ExceptionEvent> exceptionRingBuffer;
    private final SequenceBarrier exceptionSequenceBarrier;
    private final BatchEventProcessor<ExceptionEvent> exceptionStorageEventProcessor;
    /**
     * LogEntry RingBuffer
     */
    private final RingBuffer<LogEntryEvent> logEntryRingBuffer;
    private final SequenceBarrier logEntrySequenceBarrier;
    private final BatchEventProcessor<LogEntryEvent> logEntryValidateEventProcessor;
    /**
     * LogEvent RingBuffer
     */
    private final RingBuffer<LogEvent> logRingBuffer;
    private final SequenceBarrier logSequenceBarrier;
    private final BatchEventProcessor<LogEvent> logStoreEventProcessor;
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

        this.metricsRingBuffer = RingBuffer.createSingleProducer(MetricsEvent.EVENT_FACTORY, METRICS_BUFFER_SIZE, new SleepingWaitStrategy());
        this.metricsSequenceBarrier = metricsRingBuffer.newBarrier();
        this.metricsAlertEventProcessor = new BatchEventProcessor<MetricsEvent>(metricsRingBuffer, metricsSequenceBarrier, new MetricsAlertHandler());
        this.metricsAnalyzeEventProcessor = new BatchEventProcessor<MetricsEvent>(metricsRingBuffer, metricsSequenceBarrier, new MetricsAnalyzeHandler());
        this.metricsStorageEventProcessor = new BatchEventProcessor<MetricsEvent>(metricsRingBuffer, metricsSequenceBarrier, new MetricsStorageHandler());
        this.metricsRingBuffer.addGatingSequences(metricsAlertEventProcessor.getSequence());
        this.metricsRingBuffer.addGatingSequences(metricsAnalyzeEventProcessor.getSequence());
        this.metricsRingBuffer.addGatingSequences(metricsStorageEventProcessor.getSequence());

        this.exceptionRingBuffer = RingBuffer.createSingleProducer(ExceptionEvent.EVENT_FACTORY, EXCEPTION_BUFFER_SIZE, new SleepingWaitStrategy());
        this.exceptionSequenceBarrier = exceptionRingBuffer.newBarrier();
        this.exceptionStorageEventProcessor = new BatchEventProcessor<ExceptionEvent>(exceptionRingBuffer, exceptionSequenceBarrier, new ExceptionStorageHandler());

        this.logEntryRingBuffer = RingBuffer.createSingleProducer(LogEntryEvent.EVENT_FACTORY, LOGENTRY_BUFFER_SIZE, new SleepingWaitStrategy());
        this.logEntrySequenceBarrier = logEntryRingBuffer.newBarrier();
        this.logEntryValidateEventProcessor = new BatchEventProcessor<LogEntryEvent>(logEntryRingBuffer,
                logEntrySequenceBarrier,
                new LogEntryValidateHandler(traceRingBuffer,
                        metricsRingBuffer,
                        exceptionRingBuffer)
        );

        this.logRingBuffer = RingBuffer.createSingleProducer(LogEvent.EVENT_FACTORY, LOG_BUFFER_SIZE, new SleepingWaitStrategy());
        this.logSequenceBarrier = this.logRingBuffer.newBarrier();
        this.logStoreEventProcessor = new BatchEventProcessor<LogEvent>(logRingBuffer, logSequenceBarrier, new LogStorageHandler());

    }

    /**
     * Start event processors
     */
    @Override
    public void start() {
        logger.info("start message consumer base on disruptor");

        logger.info("start LogEntry validate thread");
        ExecutorService logEntryValidateExecutor = ThreadPoolUtil.newSingleThreadExecutor("logentry-validate-pool");
        logEntryValidateExecutor.execute(this.logEntryValidateEventProcessor);

        logger.info("start trace alert thread");
        ExecutorService traceAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-alert-pool");
        traceAlertExecutor.execute(this.traceAlertEventProcessor);

        logger.info("start trace analyze thread");
        ExecutorService traceAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-analyze-pool");
        traceAnalyzeExecutor.execute(this.traceAnalyzeEventProcessor);

        logger.info("start trace store thread");
        ExecutorService traceStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-store-pool");
        traceStoreExecutor.execute(this.traceStorageEventProcessor);

        logger.info("start metrics alert thread");
        ExecutorService metricsAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("metrics-alert-pool");
        metricsAlertExecutor.execute(this.metricsAlertEventProcessor);

        logger.info("start metrics analyze thread");
        ExecutorService metricsAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("metrics-analyze-pool");
        metricsAnalyzeExecutor.execute(this.metricsAnalyzeEventProcessor);

        logger.info("start metrics store thread");
        ExecutorService metricsStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("metrics-store-pool");
        metricsStoreExecutor.execute(this.metricsStorageEventProcessor);

        logger.info("start exception store thread");
        ExecutorService exceptionStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("exception-store-pool");
        exceptionStoreExecutor.execute(this.exceptionStorageEventProcessor);

        logger.info("start log store thread");
        ExecutorService logStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("log-store-pool");
        logStoreExecutor.execute(this.logStoreEventProcessor);

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
     * @param logs log4j, gc logs
     */
    @Override
    public void publish(String logs) {
        if (start && logs != null) {
            long sequence = this.logRingBuffer.next();
            this.logRingBuffer.get(sequence).setResult(logs);
            this.logRingBuffer.publish(sequence);
        }
    }

    /**
     * Stop all event processors.
     */
    @Override
    public void shutdown() {

        /**
         * close LogEntry validate thread
         */
        logEntryValidateEventProcessor.halt();

        /**
         * close trace process thread
         */
        traceAlertEventProcessor.halt();
        traceAnalyzeEventProcessor.halt();
        traceStorageEventProcessor.halt();

        /**
         * close metrics process thread
         */
        metricsAlertEventProcessor.halt();
        metricsAnalyzeEventProcessor.halt();
        metricsStorageEventProcessor.halt();

        /**
         * close exception process thread
         */
        exceptionStorageEventProcessor.halt();

        /**
         * close log process thread
         */
        logStoreEventProcessor.halt();

    }

}
