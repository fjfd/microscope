package com.vipshop.microscope.collector.processor;

import com.vipshop.microscope.thrift.LogEntry;

public class MessageProcessors {

	private static final TraceMessageProcessor messageProcessor = new TraceMessageProcessor();

	public static void process(LogEntry logEntry) {
		messageProcessor.process(logEntry);
	}

}
