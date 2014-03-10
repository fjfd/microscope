package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Metrice alert handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsAlertHandler implements EventHandler<MetricsEvent> {
	
	@Override
	public void onEvent(MetricsEvent event, long sequence, boolean endOfBatch) throws Exception {
		
	}
}