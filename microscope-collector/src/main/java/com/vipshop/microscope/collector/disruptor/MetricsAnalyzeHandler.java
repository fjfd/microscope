package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;

/**
 * Metrics analyze handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsAnalyzeHandler implements EventHandler<MetricsEvent> {
	
	private final MessageAnalyzer messageAnalyzer = MessageAnalyzer.getMessageAnalyzer();
	
	@Override
	public void onEvent(MetricsEvent event, long sequence, boolean endOfBatch) throws Exception {
		messageAnalyzer.analyze(event.getResult());
	}
}