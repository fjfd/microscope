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

    /**
     * Ring buffer size
     */
    private final int LOGENTRY_BUFFER_SIZE   = 1024 * 8 * 8 * 4;
    private final int TRACE_BUFFER_SIZE      = 1024 * 8 * 8 * 2;
    private final int METRIC_BUFFER_SIZE     = 1024 * 8 * 4 * 2;
    private final int EXCEPTION_BUFFER_SIZE  = 1024 * 8 * 2 * 1;
    private final int APPLOG_BUFFER_SIZE     = 1024 * 8 * 1 * 1;
    private final int GCLOG_BUFFER_SIZE      = 1024 * 8 * 1 * 1;

    /**
     * Trace RingBuffer
     */
    private final RingBuffer<TraceEvent> traceRingBuffer;
    private final SequenceBarrier traceSequenceBarrier;
    private final BatchEventProcessor<TraceEvent> traceAlertEventProcessor;
    private final BatchEventProcessor<TraceEvent> traceAnalyzeEventProcessor;
    private final BatchEventProcessor<TraceEvent> traceStoreEventProcessor;

    /**
     * Metric RingBuffer
     */
    private final RingBuffer<MetricEvent> metricRingBuffer;
    private final SequenceBarrier metricSequenceBarrier;
    private final BatchEventProcessor<MetricEvent> metricAlertEventProcessor;
    private final BatchEventProcessor<MetricEvent> metricAnalyzeEventProcessor;
    private final BatchEventProcessor<MetricEvent> metricStoreEventProcessor;

    /**
     * Exception RingBuffer
     */
    private final RingBuffer<ExceptionEvent> exceptionRingBuffer;
    private final SequenceBarrier exceptionSequenceBarrier;
    private final BatchEventProcessor<ExceptionEvent> exceptionAlertEventProcessor;
    private final BatchEventProcessor<ExceptionEvent> exceptionAnalyzeEventProcessor;
    private final BatchEventProcessor<ExceptionEvent> exceptionStoreEventProcessor;

    /**
     * App Log RingBuffer
     */
    private final RingBuffer<AppLogEvent> appLogRingBuffer;
    private final SequenceBarrier appLogSequenceBarrier;
    private final BatchEventProcessor<AppLogEvent> appLogAlertEventProcessor;
    private final BatchEventProcessor<AppLogEvent> appLogAnalyzeEventProcessor;
    private final BatchEventProcessor<AppLogEvent> appLogStoreEventProcessor;

    /**
     * GC Log RingBuffer
     */
    private final RingBuffer<GCLogEvent> gcLogRingBuffer;
    private final SequenceBarrier gcLogSequenceBarrier;
    private final BatchEventProcessor<GCLogEvent> gcLogAlertEventProcessor;
    private final BatchEventProcessor<GCLogEvent> gcLogAnalyzeEventProcessor;
    private final BatchEventProcessor<GCLogEvent> gcLogStoreEventProcessor;

    /**
     * LogEntry RingBuffer
     */
    private final RingBuffer<LogEntryEvent> logEntryRingBuffer;
    private final SequenceBarrier logEntrySequenceBarrier;
    private final BatchEventProcessor<LogEntryEvent> logEntryDispatchEventProcessor;

    private volatile boolean start = false;

    /**
     * Construct DisruptorMessageConsumer
     */
    public DisruptorMessageConsumer() {

        logger.info("allocate [" + LOGENTRY_BUFFER_SIZE   + "] for logentry buffer size");
        logger.info("allocate [" + TRACE_BUFFER_SIZE      + "] for trace buffer size");
        logger.info("allocate [" + EXCEPTION_BUFFER_SIZE  + "] for exception buffer size");
        logger.info("allocate [" + METRIC_BUFFER_SIZE     + "] for metric buffer size");
        logger.info("allocate [" + APPLOG_BUFFER_SIZE     + "] for applog buffer size");
        logger.info("allocate [" + GCLOG_BUFFER_SIZE      + "] for gclog buffer size");

        this.traceRingBuffer = RingBuffer.createSingleProducer(TraceEvent.EVENT_FACTORY, TRACE_BUFFER_SIZE, new SleepingWaitStrategy());
        this.traceSequenceBarrier = traceRingBuffer.newBarrier();
        this.traceAlertEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceAlertHandler());
        this.traceAnalyzeEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceAnalyzeHandler());
        this.traceStoreEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceStoreHandler());
        this.traceRingBuffer.addGatingSequences(traceAlertEventProcessor.getSequence());
        this.traceRingBuffer.addGatingSequences(traceAnalyzeEventProcessor.getSequence());
        this.traceRingBuffer.addGatingSequences(traceStoreEventProcessor.getSequence());

        this.metricRingBuffer = RingBuffer.createSingleProducer(MetricEvent.EVENT_FACTORY, METRIC_BUFFER_SIZE, new SleepingWaitStrategy());
        this.metricSequenceBarrier = metricRingBuffer.newBarrier();
        this.metricAlertEventProcessor = new BatchEventProcessor<MetricEvent>(metricRingBuffer, metricSequenceBarrier, new MetricAlertHandler());
        this.metricAnalyzeEventProcessor = new BatchEventProcessor<MetricEvent>(metricRingBuffer, metricSequenceBarrier, new MetricAnalyzeHandler());
        this.metricStoreEventProcessor = new BatchEventProcessor<MetricEvent>(metricRingBuffer, metricSequenceBarrier, new MetricStoreHandler());
        this.metricRingBuffer.addGatingSequences(metricAlertEventProcessor.getSequence());
        this.metricRingBuffer.addGatingSequences(metricAnalyzeEventProcessor.getSequence());
        this.metricRingBuffer.addGatingSequences(metricStoreEventProcessor.getSequence());

        this.exceptionRingBuffer = RingBuffer.createSingleProducer(ExceptionEvent.EVENT_FACTORY, EXCEPTION_BUFFER_SIZE, new SleepingWaitStrategy());
        this.exceptionSequenceBarrier = exceptionRingBuffer.newBarrier();
        this.exceptionAlertEventProcessor = new BatchEventProcessor<ExceptionEvent>(exceptionRingBuffer, exceptionSequenceBarrier, new ExceptionAlertHandler());
        this.exceptionAnalyzeEventProcessor = new BatchEventProcessor<ExceptionEvent>(exceptionRingBuffer, exceptionSequenceBarrier, new ExceptionAnalyzeHandler());
        this.exceptionStoreEventProcessor = new BatchEventProcessor<ExceptionEvent>(exceptionRingBuffer, exceptionSequenceBarrier, new ExceptionStoreHandler());
        this.exceptionRingBuffer.addGatingSequences(exceptionAlertEventProcessor.getSequence());
        this.exceptionRingBuffer.addGatingSequences(exceptionAnalyzeEventProcessor.getSequence());
        this.exceptionRingBuffer.addGatingSequences(exceptionStoreEventProcessor.getSequence());

        this.appLogRingBuffer = RingBuffer.createSingleProducer(AppLogEvent.EVENT_FACTORY, APPLOG_BUFFER_SIZE, new SleepingWaitStrategy());
        this.appLogSequenceBarrier = this.appLogRingBuffer.newBarrier();
        this.appLogAlertEventProcessor = new BatchEventProcessor<AppLogEvent>(appLogRingBuffer, appLogSequenceBarrier, new AppLogAlertHandler());
        this.appLogAnalyzeEventProcessor = new BatchEventProcessor<AppLogEvent>(appLogRingBuffer, appLogSequenceBarrier, new AppLogAnalyzeHandler());
        this.appLogStoreEventProcessor = new BatchEventProcessor<AppLogEvent>(appLogRingBuffer, appLogSequenceBarrier, new AppLogStoreHandler());
        this.appLogRingBuffer.addGatingSequences(appLogAlertEventProcessor.getSequence());
        this.appLogRingBuffer.addGatingSequences(appLogAnalyzeEventProcessor.getSequence());
        this.appLogRingBuffer.addGatingSequences(appLogStoreEventProcessor.getSequence());

        this.gcLogRingBuffer = RingBuffer.createSingleProducer(GCLogEvent.EVENT_FACTORY, GCLOG_BUFFER_SIZE, new SleepingWaitStrategy());
        this.gcLogSequenceBarrier = gcLogRingBuffer.newBarrier();
        this.gcLogAlertEventProcessor = new BatchEventProcessor<GCLogEvent>(gcLogRingBuffer, gcLogSequenceBarrier, new GCLogAlertHandler());
        this.gcLogAnalyzeEventProcessor = new BatchEventProcessor<GCLogEvent>(gcLogRingBuffer, gcLogSequenceBarrier, new GCLogAnalyzeHandler());
        this.gcLogStoreEventProcessor = new BatchEventProcessor<GCLogEvent>(gcLogRingBuffer, gcLogSequenceBarrier, new GCLogStoreHandler());
        this.gcLogRingBuffer.addGatingSequences(gcLogAlertEventProcessor.getSequence());
        this.gcLogRingBuffer.addGatingSequences(gcLogAnalyzeEventProcessor.getSequence());
        this.gcLogRingBuffer.addGatingSequences(gcLogStoreEventProcessor.getSequence());

        this.logEntryRingBuffer = RingBuffer.createSingleProducer(LogEntryEvent.EVENT_FACTORY, LOGENTRY_BUFFER_SIZE, new SleepingWaitStrategy());
        this.logEntrySequenceBarrier = logEntryRingBuffer.newBarrier();
        this.logEntryDispatchEventProcessor = new BatchEventProcessor<LogEntryEvent>(logEntryRingBuffer,
                                                                             logEntrySequenceBarrier,
                                                                             new LogEntryDispatchHandler(traceRingBuffer,
                                                                                                         metricRingBuffer,
                                                                                                         exceptionRingBuffer,
                                                                                                         appLogRingBuffer,
                                                                                                         gcLogRingBuffer)
        );
        this.logEntryRingBuffer.addGatingSequences(logEntryDispatchEventProcessor.getSequence());



    }

    /**
     * Start event processors
     */
    @Override
    public void start() {

        logger.info("start message consumer base on disruptor");

        logger.info("start LogEntry dispatch process thread");
        ExecutorService logEntryValidateExecutor = ThreadPoolUtil.newSingleThreadExecutor("LogEntry-dispatch-pool");
        logEntryValidateExecutor.execute(this.logEntryDispatchEventProcessor);

        logger.info("start trace alert process thread");
        ExecutorService traceAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-alert-pool");
        traceAlertExecutor.execute(this.traceAlertEventProcessor);

        logger.info("start trace analyze process thread");
        ExecutorService traceAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-analyze-pool");
        traceAnalyzeExecutor.execute(this.traceAnalyzeEventProcessor);

        logger.info("start trace store process thread");
        ExecutorService traceStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-store-pool");
        traceStoreExecutor.execute(this.traceStoreEventProcessor);

        logger.info("start metric alert process thread");
        ExecutorService metricsAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("metric-alert-pool");
        metricsAlertExecutor.execute(this.metricAlertEventProcessor);

        logger.info("start metric analyze process thread");
        ExecutorService metricsAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("metric-analyze-pool");
        metricsAnalyzeExecutor.execute(this.metricAnalyzeEventProcessor);

        logger.info("start metric store process thread");
        ExecutorService metricsStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("metric-store-pool");
        metricsStoreExecutor.execute(this.metricStoreEventProcessor);

        logger.info("start exception alert process thread");
        ExecutorService exceptionAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("exception-alert-pool");
        exceptionAlertExecutor.execute(this.exceptionAlertEventProcessor);

        logger.info("start exception analyze process thread");
        ExecutorService exceptionAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("exception-analyze-pool");
        exceptionAnalyzeExecutor.execute(this.exceptionAnalyzeEventProcessor);

        logger.info("start exception store process thread");
        ExecutorService exceptionStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("exception-store-pool");
        exceptionStoreExecutor.execute(this.exceptionStoreEventProcessor);

        logger.info("start applog alert process thread");
        ExecutorService logAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("applog-alert-pool");
        logAlertExecutor.execute(this.appLogAlertEventProcessor);

        logger.info("start applog analyze process thread");
        ExecutorService logAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("applog-analyze-pool");
        logAnalyzeExecutor.execute(this.appLogAnalyzeEventProcessor);

        logger.info("start applog store process thread");
        ExecutorService logStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("applog-store-pool");
        logStoreExecutor.execute(this.appLogStoreEventProcessor);

        logger.info("start gclog alert process thread");
        ExecutorService gcLogAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("gclog-alert-pool");
        gcLogAlertExecutor.execute(this.gcLogAlertEventProcessor);

        logger.info("start gclog analyze process thread");
        ExecutorService gcLogAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("gclog-analyze-pool");
        gcLogAnalyzeExecutor.execute(this.gcLogAnalyzeEventProcessor);

        logger.info("start gclog store process thread");
        ExecutorService gcLogStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("gclog-store-pool");
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
     * Stop all event processors.
     */
    @Override
    public void shutdown() {

        /**
         * close LogEntry process thread
         */
        logEntryDispatchEventProcessor.halt();

        /**
         * close trace process thread
         */
        traceAlertEventProcessor.halt();
        traceAnalyzeEventProcessor.halt();
        traceStoreEventProcessor.halt();

        /**
         * close metric process thread
         */
        metricAlertEventProcessor.halt();
        metricAnalyzeEventProcessor.halt();
        metricStoreEventProcessor.halt();

        /**
         * close exception process thread
         */
        exceptionAlertEventProcessor.halt();
        exceptionAnalyzeEventProcessor.halt();
        exceptionStoreEventProcessor.halt();

        /**
         * close log process thread
         */
        appLogAlertEventProcessor.halt();
        appLogAnalyzeEventProcessor.halt();
        appLogStoreEventProcessor.halt();

        /**
         * close gc log process thread
         */
        gcLogAlertEventProcessor.halt();
        gcLogAnalyzeEventProcessor.halt();
        gcLogStoreEventProcessor.halt();

    }

}
