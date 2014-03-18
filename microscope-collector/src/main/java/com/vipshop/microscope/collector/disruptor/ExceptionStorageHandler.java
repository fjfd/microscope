package com.vipshop.microscope.collector.disruptor;

import java.util.HashMap;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.logentry.LogEntryCodec;

/**
 * Exception store handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionStorageHandler implements EventHandler<ExceptionEvent> {
	
	private final MessageStorager messageStorager = MessageStorager.getMessageStorager();
	
	@Override
	public void onEvent(ExceptionEvent event, long sequence, boolean endOfBatch) throws Exception {
		String result = event.getResult();
		String[] keyValue = result.split(":");
		
		String type = type(keyValue);
		
		if (type.equals("exception")) {
			HashMap<String, Object> stack = LogEntryCodec.decodeToMap(keyValue[1].split("=")[1]);
			messageStorager.storage(stack);
		} 
		
	}

	private String type(String[] keyValue) {
		return keyValue[0].split("=")[1];
	}

}