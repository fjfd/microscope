package com.vipshop.microscope.collector.consumer;

import com.vipshop.microscope.thrift.gen.Span;

/**
 * MessageConsumer responsible for consumer spans.
 * 
 * <p>Currently, spans need be stored and analyzed.
 * 
 * @see MessageStorager
 * @see MessageAnalyzer
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface MessageConsumer {
	
	public void publish(Span span);
	
	public void start();
	
	public void shutdown();
	
}
