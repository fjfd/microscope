package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;

/**
 * Store span handler for disruptor.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class StoreEventHandler implements EventHandler<SpanEvent> {
	
	private final MessageStorager messageStorager = new MessageStorager();
	
	@Override
	public void onEvent(SpanEvent event, long sequence, boolean endOfBatch) throws Exception {
		messageStorager.storage(event.getSpan());
	}
}