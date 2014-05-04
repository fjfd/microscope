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
    public void store(Span span) {
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
    public void store(MetricData metrics) {
        storageRepository.saveMetricIndex(metrics);
        storageRepository.saveMetric(metrics);

    }

    /**
     * Store exception data.
     *
     * @param exception
     */
    public void store(ExceptionData exception) {
    }

    /**
     * Store System data.
     *
     * @param system
     */
    public void store(SystemData system) {
        storageRepository.save(system);
    }

    /**
     * Store top slow report.
     *
     * @param top
     */
    public void store(HashMap<String, Object> top) {
        storageRepository.saveTop(top);
    }

    /**
     * Store LogEntry stats report
     *
     * @param report
     */
    public void store(LogEntryReport report) {

    }



}
