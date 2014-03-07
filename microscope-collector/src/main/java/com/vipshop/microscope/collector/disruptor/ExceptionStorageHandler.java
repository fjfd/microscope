package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;

/**
 * Store exception handler for disruptor.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionStorageHandler implements EventHandler<ExceptionEvent> {
	
	private final MessageStorager messageStorager = new MessageStorager();
	
	@Override
	public void onEvent(ExceptionEvent event, long sequence, boolean endOfBatch) throws Exception {
		messageStorager.storage(event.getMap());
	}
}