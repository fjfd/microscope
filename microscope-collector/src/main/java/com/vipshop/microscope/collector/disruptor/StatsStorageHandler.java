package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Store stats handler for disruptor.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class StatsStorageHandler implements EventHandler<StatsEvent> {
	
	@Override
	public void onEvent(StatsEvent event, long sequence, boolean endOfBatch) throws Exception {
		String result = event.getResult();
		System.out.println(result);
	}
}