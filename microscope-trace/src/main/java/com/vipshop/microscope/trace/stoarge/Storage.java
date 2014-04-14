package com.vipshop.microscope.trace.stoarge;

import java.util.Map;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.common.system.SystemInfo;
import com.vipshop.microscope.common.trace.Span;

/**
 * Store message in client queue.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {
	
	/**
	 * Trace message
	 * 
	 * @param span
	 */
	public void addSpan(Span span);
	
	/**
	 * Metrics message
	 * 
	 * @param metrics
	 */
	public void addMetrics(Metric metrics);
	
	/**
	 * Exception message
	 * 
	 * @param exceptionInfo
	 */
	public void addException(Map<String, Object> exceptionInfo);

    /**
     * System info
     *
     * @param system
     */
    public void addSystemInfo(SystemInfo system);

	/**
	 * Get LogEntry from queue
	 * 
	 * @return LogEntry
	 */
	public LogEntry poll();
	
	/**
	 * Get size of queue
	 * 
	 * @return
	 */
	public int size();
	
}
