package com.vipshop.microscope.collector.processor;

import java.util.HashMap;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.trace.queue.MessageCategory;


public class MessageProcessors {

	private static final HashMap<String, MessageProcessor> processors = new HashMap<String, MessageProcessor>();
	
	static {
		processors.put(MessageCategory.TRACE, new TraceMessageProcessor());
	}
	
	public static void process(LogEntry logEntry) {
		String category = logEntry.getCategory();
		processors.get(category).process(logEntry);
	}
	
}
