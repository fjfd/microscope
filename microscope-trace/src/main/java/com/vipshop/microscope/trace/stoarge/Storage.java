package com.vipshop.microscope.trace.stoarge;

import com.vipshop.microscope.common.thrift.LogEntry;

/**
 * Storge span in client.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {
	
	public void add(LogEntry logEntry);
	
	public LogEntry poll();
	
	public int size();
}
