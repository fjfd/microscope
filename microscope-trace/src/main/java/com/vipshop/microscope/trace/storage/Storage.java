package com.vipshop.microscope.trace.storage;

import com.vipshop.microscope.trace.gen.LogEntry;
import com.vipshop.microscope.trace.gen.Span;
import com.vipshop.microscope.trace.metrics.MetricData;
import com.vipshop.microscope.trace.metrics.SystemMetric;

import java.util.Map;

/**
 * Store message in client queue.
 *
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {

    /**
     * Trace message
     *
     * @param span
     */
    public void addSpan(Span span);

    /**
     * Metrics message
     *
     * @param metrics
     */
    public void addMetrics(MetricData metrics);

    /**
     * Exception message
     *
     * @param exceptionInfo
     */
    public void addException(Map<String, Object> exceptionInfo);

    /**
     * System message
     *
     * @param system
     */
    public void addSystemMetric(SystemMetric system);

    /**
     * Get LogEntry from queue
     *
     * @return LogEntry
     */
    public LogEntry poll();

    /**
     * Get size of queue
     *
     * @return
     */
    public int size();

}
