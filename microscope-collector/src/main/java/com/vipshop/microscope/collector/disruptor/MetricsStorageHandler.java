package com.vipshop.microscope.collector.disruptor;

import java.util.HashMap;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.logentry.Codec;

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
		String result = event.getResult();
		String[] kevValue = result.split(":");
		
		String[] metricsType = kevValue[0].split("=");
		String type = metricsType[0];
		String value = metricsType[1];
		
		if (value.equals("exception")) {
			System.out.println(type);
			System.out.println(value);
			HashMap<String, Object> stack = Codec.decodeToMap(kevValue[1].split("=")[1]);
			messageStorager.storage(stack);
		}
	}
}