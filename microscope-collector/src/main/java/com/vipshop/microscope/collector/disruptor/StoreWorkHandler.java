package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;

/**
 * Store span handler for disruptor in pool model.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class StoreWorkHandler implements WorkHandler<SpanEvent> {
	
	private final MessageStorager messageStorager = new MessageStorager();
	
	@Override
	public void onEvent(SpanEvent event) throws Exception {
		messageStorager.storage(event.getSpan());
	}
}