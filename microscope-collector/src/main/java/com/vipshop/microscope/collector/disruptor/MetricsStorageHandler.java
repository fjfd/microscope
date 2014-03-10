package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Metrics store handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsStorageHandler implements EventHandler<MetricsEvent> {
	
	@Override
	public void onEvent(MetricsEvent event, long sequence, boolean endOfBatch) throws Exception {
		String result = event.getResult();
		String kevValue[] = result.split(":");
		for (String string : kevValue) {
			String keyvalue[] = string.split("=");
			System.out.println(keyvalue[0]);
			System.out.println(keyvalue[1]);
		}
	}
}