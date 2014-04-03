package com.vipshop.microscope.collector.storager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vipshop.microscope.common.metrics.MetricsCategory;
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
	 * Store span message.
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
	 * Store exception message.
	 * 
	 * @param map
	 */
	public void storageException(Map<String, Object> map) {
		storageRepository.saveException(map);
	}
	
	public void storageCounter(HashMap<String, Object> counter) {
		HashMap<String, Object> servletCounter = new HashMap<String, Object>();
		for (Entry<String, Object> entry : counter.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.Servlet_Active_Request)) {
				servletCounter.put(entry.getKey(), entry.getValue());
			}
		}
		
		servletCounter.put("ip", counter.get("ip"));
		servletCounter.put("app", counter.get("app"));
		servletCounter.put("date", counter.get("date"));
		
		storageRepository.saveServletActiveRequest(servletCounter);
	}
	
	public void storageGauge(HashMap<String, Object> gauge) {
		HashMap<String, Object> jvmGauge = new HashMap<String, Object>();
		
		for (Entry<String, Object> entry : gauge.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.JVM)) {
				jvmGauge.put(entry.getKey(), entry.getValue());
			}
		}
		
		jvmGauge.put("ip", gauge.get("ip"));
		jvmGauge.put("app", gauge.get("app"));
		jvmGauge.put("date", gauge.get("date"));
		
		storageRepository.saveJVM(jvmGauge);
	}
	
	public void storageHistogram(HashMap<String, Object> histogram) {
		
	}
	
	public void storageMeter(HashMap<String, Object> meter) {
		HashMap<String, Object> servletMeter = new HashMap<String, Object>();
		for (Entry<String, Object> entry : meter.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.Servlet_Response_Code)) {
				servletMeter.put(entry.getKey(), entry.getValue());
			}
		}
		
		servletMeter.put("ip", meter.get("ip"));
		servletMeter.put("app", meter.get("app"));
		servletMeter.put("date", meter.get("date"));
		
		storageRepository.saveServletResponseCode(servletMeter);
		
	}
	
	public void storageTimer(HashMap<String, Object> timer) {
		HashMap<String, Object> servletTimer = new HashMap<String, Object>();
		for (Entry<String, Object> entry : timer.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.Servlet_Request)) {
				servletTimer.put(entry.getKey(), entry.getValue());
			}
		}

		servletTimer.put("ip", timer.get("ip"));
		servletTimer.put("app", timer.get("app"));
		servletTimer.put("date", timer.get("date"));
		
		storageRepository.saveServletRequest(servletTimer);
	}
	
	public void storageHealth(HashMap<String, Object> health) {
		
	}
	
}
