package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.span.Category;
import com.vipshop.microscope.trace.span.SpanBuilder;
import com.vipshop.microscope.trace.span.SpanContext;
import com.vipshop.microscope.trace.span.SpanId;
import com.vipshop.microscope.trace.swith.Switch;

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
	
	/**
	 * Get current {@code SpanId}.
	 * 
	 * @return
	 */
	public SpanId getSpanId() {
		return spanBuilder.getSpanId();
	}
	
	/**
	 * Start a new Span.
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
	 * Start a new Span.
	 * 
	 * @param spanName
	 * @param serverIP
	 * @param category
	 */
	public void clientSend(String spanName, String serverIP, Category category) {
		if (Switch.isClose()) {
			return;
		}
		spanBuilder.clientSend(spanName, serverIP, category);
	}
	
	/**
	 * Set span ResultCode.
	 * 
	 * @param result
	 */
	public void setResutlCode(String result) {
		if (Switch.isClose()) {
			return;
		}
		spanBuilder.setResultCode(result);
	}
	
	/**
	 * Complete a Span.
	 */
	public void clientReceive() {
		if (Switch.isClose()) {
			return;
		}
		spanBuilder.clientReceive();
	}
	
	/**
	 * Record key/value for debug.
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
