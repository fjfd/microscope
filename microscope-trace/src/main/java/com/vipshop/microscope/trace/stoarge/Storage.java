package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;

/**
 * Storge span in client.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {
	
	public void add(Span span);
	
	public void add(HashMap<String, Object> map);
	
	public void add(LogEntry logEntry);
	
	public LogEntry poll();
	
	public int size();
}
