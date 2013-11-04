package com.vipshop.microscope.collector.processor;

import com.vipshop.microscope.thrift.LogEntry;

public abstract class AbstraceMessageProcessor {
	
	public abstract void process(LogEntry logEntry);
}
