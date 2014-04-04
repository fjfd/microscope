package com.vipshop.microscope.collector.storager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vipshop.microscope.common.logentry.Constants;
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
	 * Store exception message.
	 * 
	 * @param map
	 */
	public void storageException(Map<String, Object> map) {
		storageRepository.saveExceptionIndex(map);
		storageRepository.saveException(map);
	}
	
	/**
	 * Store counter message.
	 * 
	 * @param counter
	 */
	public void storageCounter(HashMap<String, Object> counter) {
		HashMap<String, Object> servletCounter = new HashMap<String, Object>();
		for (Entry<String, Object> entry : counter.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.Servlet_Active_Request)) {
				servletCounter.put(entry.getKey(), entry.getValue());
			}
		}
		
		if (servletCounter.entrySet().size() != 0) {
			servletCounter.put(Constants.APP, counter.get(Constants.APP));
			servletCounter.put(Constants.IP, counter.get(Constants.IP));
			servletCounter.put(Constants.DATE, counter.get(Constants.DATE));
			storageRepository.saveServletActiveRequest(servletCounter);
		}
		
	}
	
	public void storageGauge(HashMap<String, Object> gauge) {
		HashMap<String, Object> jvmGauge = new HashMap<String, Object>();
		
		for (Entry<String, Object> entry : gauge.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.JVM)) {
				jvmGauge.put(entry.getKey(), entry.getValue());
			}
		}
		
		if (jvmGauge.entrySet().size() != 0) {
			jvmGauge.put(Constants.APP, gauge.get(Constants.APP));
			jvmGauge.put(Constants.IP, gauge.get(Constants.IP));
			jvmGauge.put(Constants.DATE, gauge.get(Constants.DATE));
			jvmGauge.put(Constants.REPORT, MetricsCategory.JVM);
			storageRepository.saveReportIndex(jvmGauge);
			storageRepository.saveJVM(jvmGauge);
		}
		
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
		
		if (servletMeter.entrySet().size() != 0) {
			servletMeter.put(Constants.APP, meter.get(Constants.APP));
			servletMeter.put(Constants.IP, meter.get(Constants.IP));
			servletMeter.put(Constants.DATE, meter.get(Constants.DATE));
			servletMeter.put(Constants.REPORT, MetricsCategory.Servlet);
			storageRepository.saveReportIndex(servletMeter);
			storageRepository.saveServletResponseCode(servletMeter);
		}
		
		
	}
	
	public void storageTimer(HashMap<String, Object> timer) {
		HashMap<String, Object> servletTimer = new HashMap<String, Object>();
		for (Entry<String, Object> entry : timer.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.Servlet_Request)) {
				servletTimer.put(entry.getKey(), entry.getValue());
			}
		}
		
		if (servletTimer.entrySet().size() != 0) {
			servletTimer.put(Constants.APP, timer.get(Constants.APP));
			servletTimer.put(Constants.IP, timer.get(Constants.IP));
			servletTimer.put(Constants.DATE, timer.get(Constants.DATE));
			servletTimer.put(Constants.REPORT, MetricsCategory.Servlet);
			storageRepository.saveReportIndex(servletTimer);
			storageRepository.saveServletRequest(servletTimer);
		}
	}
	
	public void storageHealth(HashMap<String, Object> health) {
		
	}
	
}
