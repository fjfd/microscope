package com.vipshop.microscope.collector.storager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.hbase.domain.AppTable;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;

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
	 * Store span message.
	 * 
	 * @param span
	 */
	public void storageTrace(Span span) {
		String traceId = String.valueOf(span.getTraceId());
		String spanId = String.valueOf(span.getSpanId());
		if (traceId.equals(spanId)) {
			storageRepository.save(AppTable.build(span));
			storageRepository.save(TraceTable.build(span));
		}
		storageRepository.save(span);
	}
	
	/**
	 * Store exception message.
	 * 
	 * @param map
	 */
	public void storageException(Map<String, Object> map) {
		storageRepository.saveException(map);
	}
	
	public void storageCounter(HashMap<String, Object> counter) {
		
	}
	
	public void storageGauge(HashMap<String, Object> gauge) {
		HashMap<String, Object> jvm = new HashMap<String, Object>();
		
		for (Entry<String, Object> entry : gauge.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.JVM)) {
				jvm.put(entry.getKey(), entry.getValue());
			}
		}
		
		jvm.put("ip", gauge.get("ip"));
		jvm.put("app", gauge.get("app"));
		jvm.put("date", gauge.get("date"));
		
		storageRepository.saveJVM(jvm);
	}
	
	public void storageHistogram(HashMap<String, Object> histogram) {
		
	}
	
	public void storageMeter(HashMap<String, Object> meter) {
		
	}
	
	public void storageTimer(HashMap<String, Object> timer) {
		
	}
	
	public void storageHealth(HashMap<String, Object> health) {
		
	}
	
}
