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
	
	/**
	 * Start consumer
	 */
	public void start();

	/**
	 * Publish span to consumer.
	 * 
	 * @param span span object
	 */
	public void publish(Span span);
	
	/**
	 * Stop consumer.
	 */
	public void shutdown();
	
}
