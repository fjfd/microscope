package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.vipshop.microscope.collector.stats.MesssageStats;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.collector.validater.MessageValidater;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.Codec;
import com.vipshop.microscope.trace.Constants;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogEntry handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryHandler implements EventHandler<LogEntryEvent> {

    public final Logger logger = LoggerFactory.getLogger(LogEntryHandler.class);

    private final MessageValidater messageValidater = new MessageValidater();
    private final MesssageStats stats = new MesssageStats();

    private RingBuffer<TraceEvent> traceRingBuffer;
    private RingBuffer<MetricEvent> metricsRingBuffer;
    private RingBuffer<ExceptionEvent> exceptionRingBuffer;

    public LogEntryHandler(RingBuffer<TraceEvent> traceBuffer,
                           RingBuffer<MetricEvent> metricsBuffer,
                           RingBuffer<ExceptionEvent> exceptionRingBuffer) {
        this.traceRingBuffer = traceBuffer;
        this.metricsRingBuffer = metricsBuffer;
        this.exceptionRingBuffer = exceptionRingBuffer;
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
            Span span = null;
            try {
                span = Codec.toSpan(logEntry.getMessage());
            } catch (Exception e) {
                logger.error("decode to Span error, ignore this trace data ", e);
                return;
            }
            span = messageValidater.validate(span);
            publish(span);
            return;
        }

        /**
         * handle metric data
         */
        if (category.equals(Constants.METRIC)) {
            MetricData metrics = null;
            try {
                metrics = Codec.toMetricData(logEntry.getMessage());
            } catch (Exception e) {
                logger.error("decode to MetricData error, ignore this metric data ", e);
                return;
            }
            // TODO validate
            publish(metrics);
            return;
        }

        /**
         * handle exception data
         */
        if (category.equals(Constants.EXCEPTION)) {
            ExceptionData exception = null;
            try {
                exception = Codec.toExceptionData(logEntry.getMessage());
            } catch (Exception e) {
                logger.error("decode to ExceptionData error, ignore this exception data ", e);
                // TODO: handle exception
                return;
            }
            publish(exception);
            return;
        }

        /**
         * handle system data
         */
        if (category.equals(Constants.SYSTEM)) {
            SystemData system = null;
            try {
                system = Codec.toSystemData(logEntry.getMessage());
            } catch (Exception e) {
                logger.error("decode to SystemData error, ignore this system data ", e);
                return;
            }
            publish(system);
            return;
        }
    }

    /**
     * Publish trace to {@code TraceRingBuffer}.
     *
     * @param span
     */
    private void publish(Span span) {
        if (span != null) {
            long sequence = this.traceRingBuffer.next();
            this.traceRingBuffer.get(sequence).setSpan(span);
            this.traceRingBuffer.publish(sequence);
        }
    }

    /**
     * Publish metrics to {@code MetricsRingBuffer}.
     *
     * @param metrics
     */
    private void publish(MetricData metrics) {
        if (metrics != null) {
            long sequence = this.metricsRingBuffer.next();
            this.metricsRingBuffer.get(sequence).setResult(metrics);
            this.metricsRingBuffer.publish(sequence);
        }
    }

    /**
     * Publish exception to {@code ExceptionRingBuffer}.
     *
     * @param exception
     */
    private void publish(ExceptionData exception) {
        if (exception != null) {
            long sequence = this.exceptionRingBuffer.next();
            this.exceptionRingBuffer.get(sequence).setResult(exception);
            this.exceptionRingBuffer.publish(sequence);
        }
    }

    /**
     * Store System info directly.
     *
     * Because this info only once for a host,
     * so there is no need to put in buffer.
     */
    private void publish(SystemData system) {
        MessageStorager.getMessageStorager().save(system);
    }

}