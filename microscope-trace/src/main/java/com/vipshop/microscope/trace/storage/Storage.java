package com.vipshop.microscope.trace.storage;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;

/**
 * Store message in client memory or disk.
 *
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {

    /**
     * Put trace data to storage
     *
     * @param span data of trace
     */
    void addTraceData(Span span);

    /**
     * Put metric data to storage
     *
     * @param metric data of metric
     */
    void addMetricData(MetricData metric);

    /**
     * Put exception data to storage
     *
     * @param exception data of exception
     */
    void addExceptionData(ExceptionData exception);

    /**
     * Put system data to storage
     *
     * @param system data of system
     */
    void addSystemData(SystemData system);

    /**
     * Get LogEntry from storage
     *
     * @return LogEntry
     */
    LogEntry poll();

}
