package com.vipshop.microscope.collector.disruptor;

import java.util.HashMap;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.metrics.MetricsCategory;

/**
 * Metrics store handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsStorageHandler implements EventHandler<MetricsEvent> {
	
	private final MessageStorager messageStorager = MessageStorager.getMessageStorager();
	
	@Override
	public void onEvent(MetricsEvent event, long sequence, boolean endOfBatch) throws Exception {
		
		HashMap<String, Object> metrics = event.getResult();
		
		String metricsType = (String) metrics.get("type");
		
		if (metricsType.equals(MetricsCategory.THREAD)) {
			processThreadMetrics(metrics);
			return;
		}
		
		if (metricsType.equals(MetricsCategory.Memory)) {
			processMemoryMetrics(metrics);
			return;
		}
		
		if (metricsType.equals(MetricsCategory.GG)) {
			processGCMetrics(metrics);
			return;
		}
		
		System.out.println(metrics);
		
//		String result = event.getResult();
//		String[] keyValue = result.split(":");
//		
//		String type = type(keyValue);
//		
//		if (type.equals("exception")) {
//			HashMap<String, Object> stack = Codec.decodeToMap(keyValue[1].split("=")[1]);
//			messageStorager.storage(stack);
//		} 
//		
//		if (type.equals("gauge")) {
//			String[] date = keyValue[1].split("=");
//
//			HashMap<String, String> jvm = new HashMap<String, String>();
//			jvm.put(date[0], date[1]);
//			for (int i = 2; i < keyValue.length; i++) {
//				if (keyValue[i].contains("JVM")) {
//					String[] jvmdata = keyValue[i].split("=");
//					jvm.put(jvmdata[0], jvmdata[1]);
//				}
//			}
//		}
	}
	
	private void processGCMetrics(HashMap<String, Object> metrics) {
		// TODO Auto-generated method stub
		
	}

	private void processMemoryMetrics(HashMap<String, Object> metrics) {
		// TODO Auto-generated method stub
		
	}

	private void processThreadMetrics(HashMap<String, Object> metrics) {
		// TODO Auto-generated method stub
		
	}

}