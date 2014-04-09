package com.vipshop.microscope.collector.storager;

import java.util.Map;

import com.vipshop.microscope.common.metrics.Metric;
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
	public void storageTrace(Span span) {
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
	public void storageMetrics(Metric metrics) {
		String metric  = metrics.getMetric();
		long timestamp = metrics.getTimestamp();
		Map<String, String> tags = metrics.getTags();
		Object value = metrics.getValue();
		
		if (value instanceof Long) {
			storageRepository.add(metric, timestamp, (Long)value, tags);
		}
		
		if (value instanceof Double) {
			storageRepository.add(metric, timestamp, (Double)value, tags);
		}
		
		if (value instanceof Float) {
			storageRepository.add(metric, timestamp, (Float)value, tags);
		}
	}
	
	/**
	 * Store exception message.
	 * 
	 * @param map
	 */
	public void storageException(Map<String, Object> map) {
		storageRepository.saveExceptionIndex(map);
		storageRepository.saveException(map);
	}
	
}
