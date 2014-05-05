package com.vipshop.microscope.collector.storager;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.hbase.LogEntryReport;
import com.vipshop.microscope.storage.hbase.TraceIndexTable;
import com.vipshop.microscope.storage.hbase.TraceOverviewTable;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;

import java.util.HashMap;
import java.util.Map;

/**
 * Message Store API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MessageStorager {

    /**
     * the main storage executor.
     */
    private StorageRepository storageRepository = StorageRepository.getStorageRepository();

    private MessageStorager() {
    }

    public static MessageStorager getMessageStorager() {
        return MessageStoragerHolder.messageStorager;
    }

    private static class MessageStoragerHolder {
        private static final MessageStorager messageStorager = new MessageStorager();
    }

    /**
     * Store trace data.
     *
     * @param span
     */
    public void save(Span span) {
        String traceId = String.valueOf(span.getTraceId());
        String spanId = String.valueOf(span.getSpanId());
        if (traceId.equals(spanId)) {
            storageRepository.save(TraceIndexTable.build(span));
            storageRepository.save(TraceOverviewTable.build(span));
        }
        storageRepository.save(span);
    }

    /**
     * Store metric data
     *
     * @param metrics
     */
    public void save(MetricData metrics) {
        String metric = metrics.getMetric();
        long timestamp = metrics.getTimestamp();
        Map<String, String> tags = metrics.getTags();
        Object value = metrics.getValue();
        storageRepository.save(metric, timestamp, value, tags);
    }

    /**
     * Store exception data.
     *
     * @param exception
     */
    public void save(ExceptionData exception) {
    }

    /**
     * Store System data.
     *
     * @param system
     */
    public void save(SystemData system) {
        storageRepository.save(system);
    }

    /**
     * Store top slow report.
     *
     * @param top
     */
    public void save(HashMap<String, Object> top) {

    }

    /**
     * Store LogEntry stats report
     *
     * @param report
     */
    public void save(LogEntryReport report) {

    }



}
