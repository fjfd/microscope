package com.vipshop.microscope.collector.processor;

import java.util.HashMap;
import java.util.Map;

import com.vipshop.microscope.common.codec.MessageCategory;
import com.vipshop.microscope.thrift.LogEntry;

public class MessageProcessors {
	
	private static final Map<String, AbstraceMessageProcessor> container = new HashMap<String, AbstraceMessageProcessor>();
	
	static {
		container.put(MessageCategory.TRACE, new TraceMessageProcessor());
	}

	public static void process(LogEntry logEntry) {
		String category = logEntry.getCategory();
		container.get(category).process(logEntry);
	}

}
