package com.vipshop.microscope.collector.processor;

import com.vipshop.microscope.thrift.LogEntry;

public interface MessageProcessor {
	
	public void process(LogEntry logEntry);
	
	public void stat(LogEntry logEntry);

	public void store(String msg);
	
	
}
