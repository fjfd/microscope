package com.vipshop.microscope.collector.validater;

import java.util.HashMap;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;

/**
 * Validate message from client.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageValidater {
	
	private static class MessageValidaterHolder {
		private static MessageValidater messageValidater = new MessageValidater();
	}
	
	public static MessageValidater getMessageValidater() {
		return MessageValidaterHolder.messageValidater;
	}

	private final TraceMessageValidater traceMessageValidater = new TraceMessageValidater();
	private final MetricsMessageValidater metricsMessageValidater = new MetricsMessageValidater();
	
	private MessageValidater() {}
	
	public void validate(LogEntry logEntry) {
		
	}
	
	public Span validateMessage(Span span) {
		return traceMessageValidater.validate(span);
	}
	
	public HashMap<String, Object> validateMessage(HashMap<String, Object> metrics) {
		return metricsMessageValidater.validate(metrics);
	}
}
