package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;

/**
 * Storge metrics in client queue.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {
	
	/**
	 * Metrics
	 * 
	 * @param metrics
	 */
	public void addMetrics(HashMap<String, Object> metrics);
	
	/**
	 * Trace
	 * 
	 * @param span
	 */
	public void addSpan(Span span);
	
	/**
	 * Put object to queue
	 * 
	 * @param logEntry
	 */
	public void add(Object object);

	/**
	 * Get LogEntry from queue
	 * @return
	 */
	public LogEntry poll();
	
	/**
	 * Get size of queue
	 * 
	 * @return
	 */
	public int size();
	
}
