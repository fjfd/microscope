package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.vipshop.microscope.collector.analyzer.StatsAnalyzer;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.collector.validater.MessageValidater;
import com.vipshop.microscope.common.cons.Constants;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.client.codec.Codec;
import com.vipshop.microscope.client.exception.ExceptionData;
import com.vipshop.microscope.client.metric.MetricData;
import com.vipshop.microscope.client.system.SystemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogEntry dispatch handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryDispatchHandler implements EventHandler<LogEntryEvent> {

    public final Logger logger = LoggerFactory.getLogger(LogEntryDispatchHandler.class);

    private final StatsAnalyzer stats = new StatsAnalyzer();

    private RingBuffer<TraceEvent> traceRingBuffer;
    private RingBuffer<MetricEvent> metricRingBuffer;
    private RingBuffer<ExceptionEvent> exceptionRingBuffer;
    private RingBuffer<AppLogEvent> appLogRingBuffer;
    private RingBuffer<GCLogEvent> gcLogRingBuffer;


    public LogEntryDispatchHandler(RingBuffer<TraceEvent> traceBuffer,
                                   RingBuffer<MetricEvent> metricsBuffer,
                                   RingBuffer<ExceptionEvent> exceptionRingBuffer,
                                   RingBuffer<AppLogEvent> appLogRingBuffer,
                                   RingBuffer<GCLogEvent> gcLogRingBuffer) {
        this.traceRingBuffer = traceBuffer;
        this.metricRingBuffer = metricsBuffer;
        this.exceptionRingBuffer = exceptionRingBuffer;
        this.appLogRingBuffer = appLogRingBuffer;
        this.gcLogRingBuffer = gcLogRingBuffer;
    }

    @Override
    public void onEvent(LogEntryEvent event, long sequence, boolean endOfBatch) throws Exception {

        LogEntry logEntry = event.getResult();

        /**
         * stats LogEntry size and number
         */
        stats.stats(logEntry);

        String category = logEntry.getCategory();

        /**
         * handle trace data
         */
        if (category.equals(Constants.TRACE)) {
            dispatchTrace(logEntry.getMessage());
            return;
        }

        /**
         * handle metric data
         */
        if (category.equals(Constants.METRIC)) {
            dispatchMetric(logEntry.getMessage());
            return;
        }

        /**
         * handle exception data
         */
        if (category.equals(Constants.EXCEPTION)) {
            dispatchException(logEntry.getMessage());
            return;
        }

        /**
         * handle system data
         */
        if (category.equals(Constants.SYSTEM)) {
            dispatchSystem(logEntry.getMessage());
            return;
        }

        /**
         * handle application logs
         */
        if (category.equals(Constants.LOG)) {
            dispatchAppLog(logEntry.getMessage());
            return;
        }

        /**
         * handle gc logs
         */
        if (category.equals(Constants.GCLOG)) {
            dispatchGCLog(logEntry.getMessage());
            return;
        }
    }

    /**
     * Publish trace data to {@code TraceRingBuffer}.
     *
     * @param msg
     */
    private void dispatchTrace(String msg) {
        Span span = null;

        try {
            span = Codec.toSpan(msg);
        } catch (Exception e) {
            logger.error("decode to Span error, ignore this trace data ", e);
            return;
        }

        span = MessageValidater.validate(span);

        if (span != null) {
            long sequence = this.traceRingBuffer.next();
            this.traceRingBuffer.get(sequence).setSpan(span);
            this.traceRingBuffer.publish(sequence);
        }
    }

    /**
     * Publish metric to {@code MetricRingBuffer}.
     *
     * @param msg
     */
    private void dispatchMetric(String msg) {
        MetricData metrics = null;

        try {
            metrics = Codec.toMetricData(msg);
        } catch (Exception e) {
            logger.error("decode to MetricData error, ignore this metric data ", e);
            return;
        }

        metrics = MessageValidater.validate(metrics);

        if (metrics != null) {
            long sequence = this.metricRingBuffer.next();
            this.metricRingBuffer.get(sequence).setResult(metrics);
            this.metricRingBuffer.publish(sequence);
        }
    }

    /**
     * Publish exception to {@code ExceptionRingBuffer}.
     *
     * @param msg
     */
    private void dispatchException(String msg) {

        ExceptionData exception = null;

        try {
            exception = Codec.toExceptionData(msg);
        } catch (Exception e) {
            logger.error("decode to ExceptionData error, ignore this exception data ", e);
            return;
        }

        exception = MessageValidater.validate(exception);

        if (exception != null) {
            long sequence = this.exceptionRingBuffer.next();
            this.exceptionRingBuffer.get(sequence).setResult(exception);
            this.exceptionRingBuffer.publish(sequence);
        }
    }

    /**
     * Store System data directly.
     *
     * Because system data collected only once a time,
     * so there is no need to put it in ring buffer.
     */
    private void dispatchSystem(String msg) {

        SystemData system = null;

        try {
            system = Codec.toSystemData(msg);
        } catch (Exception e) {
            logger.error("decode to SystemData error, ignore this system data ", e);
            return;
        }

        system = MessageValidater.validate(system);

        if(system != null) {
            MessageStorager.getMessageStorager().save(system);
        }

    }

    /**
     * Publish app logs to {@code AppLogRingBuffer}
     *
     * @param logs app logs
     */
    private void dispatchAppLog(String logs) {

        logs = MessageValidater.validateAppLog(logs);

        if (logs != null) {
            long sequence = this.appLogRingBuffer.next();
            this.appLogRingBuffer.get(sequence).setResult(logs);
            this.appLogRingBuffer.publish(sequence);
        }
    }

    /**
     * Publish gc logs to {@code GCLogRingBuffer}
     *
     * @param logs gc logs
     */
    private void dispatchGCLog(String logs) {

        logs = MessageValidater.validateGCLog(logs);

        if (logs != null) {
            long sequence = this.gcLogRingBuffer.next();
            this.gcLogRingBuffer.get(sequence).setResult(logs);
            this.gcLogRingBuffer.publish(sequence);
        }
    }


}