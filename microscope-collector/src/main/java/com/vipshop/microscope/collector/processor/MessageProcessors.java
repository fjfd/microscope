package com.vipshop.microscope.collector.processor;

import java.util.HashMap;
import java.util.Map;

import com.vipshop.microscope.common.codec.MessageCategory;
import com.vipshop.microscope.thrift.LogEntry;

public class MessageProcessors {
	
	private static final Map<String, AbstraceMessageProcessor> container = new HashMap<String, AbstraceMessageProcessor>();
	
	static {
		container.put(MessageCategory.TRACE, new TraceMessageProcessor());
		container.put(MessageCategory.EVENT, new EventMessageProcessor());
		container.put(MessageCategory.PROBLEM, new ProblemMessageProcessor());
		container.put(MessageCategory.HEARTBEAT, new HeartbeatMessageProcessor());
	}

	public static void process(LogEntry logEntry) {
		String category = logEntry.getCategory();
		container.get(category).process(logEntry);
	}

}
