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
		
		String type = type(kevValue);
		
		if (type.equals("exception")) {
			HashMap<String, Object> stack = Codec.decodeToMap(stackValue(kevValue));
			messageStorager.storage(stack);
		} else {
			for (int i = 1; i < kevValue.length; i++) {
//				System.out.println(kevValue[i]);
			}
		}
	}
	
	private String type(String[] kevValue) {
		return kevValue[0];
	}
	
	private String stackValue(String[] kevValue) {
		return kevValue[1].split("=")[1];
	}
}