package com.vipshop.microscope.collector.consumer;

import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.thrift.LogEntry;

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
	 * Publish logEntry to consumer.
	 * 
	 * @param logEntry
	 */
	public void publish(LogEntry logEntry);
	
	/**
	 * Stop consumer.
	 */
	public void shutdown();
	
}
