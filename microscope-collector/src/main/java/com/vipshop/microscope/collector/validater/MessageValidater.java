package com.vipshop.microscope.collector.validater;

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
	
	public Span validateMessage(Span span) {
		return traceMessageValidater.validate(span);
	}
	
	public String validateMessage(String msg) {
		return metricsMessageValidater.validate(msg);
	}
}
