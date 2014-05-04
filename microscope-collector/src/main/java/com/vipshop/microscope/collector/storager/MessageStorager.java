package com.vipshop.microscope.collector.storager;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.hbase.report.LogEntryReport;
import com.vipshop.microscope.storage.hbase.table.TraceIndexTable;
import com.vipshop.microscope.storage.hbase.table.TraceOverviewTable;
import com.vipshop.microscope.thrift.Span;
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

    /**
     * Store trace message.
     *
     * @param span
     */
    public void storeTrace(Span span) {
        String traceId = String.valueOf(span.getTraceId());
        String spanId = String.valueOf(span.getSpanId());
        if (traceId.equals(spanId)) {
            storageRepository.save(TraceIndexTable.build(span));
            storageRepository.save(TraceOverviewTable.build(span));
        }
        storageRepository.save(span);
    }

    /**
     * Store metrics message
     *
     * @param metrics
     */
    public void storeMetrics(MetricData metrics) {
//		String metric  = metrics.getMetric();
//		long timestamp = metrics.getTimestamp();
//		Map<String, String> tags = metrics.getTags();
//		Object value = metrics.getValue();

//		if (value instanceof Long) {
//			storageRepository.add(metric, timestamp, (Long)value, tags);
//		}
//
//		if (value instanceof Double) {
//			storageRepository.add(metric, timestamp, (Double)value, tags);
//		}
//
//		if (value instanceof Float) {
//			storageRepository.add(metric, timestamp, (Float)value, tags);
//		}

        storageRepository.saveMetricIndex(metrics);
        storageRepository.saveMetric(metrics);

    }

    /**
     * Store exception message.
     *
     * @param map
     */
    public void storeException(Map<String, Object> map) {
        storageRepository.saveExceptionIndex(map);
        storageRepository.saveException(map);
    }

    /**
     * Store SystemMetric message.
     *
     * @param info
     */
    public void storeSystemInfo(SystemData info) {
        storageRepository.save(info);
    }

    /**
     * Store top report.
     *
     * @param top
     */
    public void storeTopReport(HashMap<String, Object> top) {
        storageRepository.saveTop(top);
    }

    public void storeLogEntryReport(LogEntryReport report) {

    }

    private static class MessageStoragerHolder {
        private static final MessageStorager messageStorager = new MessageStorager();
    }

}
