package com.vipshop.microscope.trace;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.span.SpanBuilder;
import com.vipshop.microscope.trace.span.SpanContext;
import com.vipshop.microscope.trace.span.SpanId;

/**
 * Trace operations.
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
	 * A new trace with default null context.
	 */
	public Trace() {
		this.spanBuilder = new SpanBuilder();
	}
	
	/**
	 * A new trace with deliver context.
	 * 
	 * @param context deliver context
	 */
	public Trace(SpanContext context) {
		this.spanBuilder = new SpanBuilder(context);
	}
	
	/**
	 * Get {@code SpanId} for current thread.
	 * 
	 * @return
	 */
	public SpanId getSpanId() {
		return spanBuilder.getSpanId();
	}
	
	/**
	 * Start a new Span.
	 * 
	 * @param spanName span name
	 * @param category span category
	 */
	public void clientSend(String spanName, Category category) {
		spanBuilder.clientSend(spanName, category);
	}
	
	/**
	 * Start a new Span.
	 * 
	 * @param spanName span name
	 * @param server server name/IP
	 * @param category span category
	 */
	public void clientSend(String spanName, String server, Category category) {
		spanBuilder.clientSend(spanName, server, category);
	}
	
	/**
	 * Set span ResultCode.
	 * 
	 * <p>If exception happens, should set 
	 * result status to exception. 
	 *
	 * @param result
	 */
	public void setResutlCode(String result) {
		spanBuilder.setResultCode(result);
	}
	
	/**
	 * Complete a Span.
	 */
	public void clientReceive() {
		spanBuilder.clientReceive();
	}
	
	/**
	 * Add debug info
	 * 
	 * @param key
	 * @param value
	 */
	public void addDebug(String key, String value) {
		spanBuilder.addDebug(key, value);
	}
	
}
