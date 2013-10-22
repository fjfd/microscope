package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.span.Category;
import com.vipshop.microscope.trace.span.SpanBuilder;
import com.vipshop.microscope.trace.span.SpanContext;
import com.vipshop.microscope.trace.span.SpanId;

/**
 * Tracing client API for Java.
 * 
 * <p> Basically, we use {@code collector} as our backend system,
 * we build a java tracing client to collector message, use
 * {@code ThreadTransporter} transport spans to {@code collector}.
 * 
 * <p> Application programmers should use this API in app code
 * if necessary. But in some case, we will embed tracing API
 * to framework if possible.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class Trace {
	
	/**
	 * SpanBuilder use to build span object.
	 */
	private SpanBuilder spanBuilder;
	
	/**
	 * A new trace with default context.
	 */
	public Trace() {
		this.spanBuilder = new SpanBuilder();
	}
	
	/**
	 * A new trace with context information.
	 * 
	 * @param spanId
	 * @param spanBuilder
	 */
	public Trace(SpanContext context) {
		this.spanBuilder = new SpanBuilder(context);
	}
	
	public SpanId getSpanId() {
		return spanBuilder.getSpanId();
	}
	
	/**
	 * Start a new client span with category.
	 * 
	 * @param spanName
	 * @param category
	 */
	public void clientSend(String spanName, Category category) {
		if (Switch.isClose()) {
			return;
		}
		spanBuilder.clientSend(spanName, category);
	}
	
	/**
	 * Complete a span.
	 */
	public void clientReceive() {
		if (Switch.isClose()) {
			return;
		}
		spanBuilder.clientReceive();
	}
	
	/**
	 * Record key/value pair.
	 * 
	 * @param key
	 * @param value
	 */
	public void record(String key, String value) {
		if (Switch.isClose()) {
			return;
		}
		spanBuilder.buildKeyValue(key, value);
	}
	
}
