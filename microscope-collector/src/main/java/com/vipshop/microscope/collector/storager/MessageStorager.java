package com.vipshop.microscope.collector.storager;

import java.util.HashMap;
import java.util.Map;

import com.vipshop.microscope.storage.hbase.report.LogEntryReport;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.common.system.SystemInfo;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.hbase.table.TraceIndexTable;
import com.vipshop.microscope.storage.hbase.table.TraceOverviewTable;

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

	private static class MessageStoragerHolder {
		private static final MessageStorager messageStorager = new MessageStorager();
	}
	
	public static MessageStorager getMessageStorager() {
		return MessageStoragerHolder.messageStorager;
	}
	
	private MessageStorager() {}
	
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
	public void storeMetrics(Metric metrics) {
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
     * Store SystemInfo message.
     *
     * @param info
     */
    public void storeSystemInfo(SystemInfo info) {
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
	
}
