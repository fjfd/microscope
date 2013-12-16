package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;

/**
 * Analyze span handler for disruptor.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class AnalyzeEventHandler implements EventHandler<SpanEvent> {
	
	private final MessageAnalyzer messageAnalyzer = new MessageAnalyzer();
	
	public void onEvent(SpanEvent event, long sequence, boolean endOfBatch) throws Exception {
		messageAnalyzer.analyze(event.getSpan());
	}
	
}