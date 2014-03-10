package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Metrics analyze handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsAnalyzeHandler implements EventHandler<MetricsEvent> {
	
	@Override
	public void onEvent(MetricsEvent event, long sequence, boolean endOfBatch) throws Exception {

	}
}