package com.vipshop.microscope.trace;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.span.SpanBuilder;
import com.vipshop.microscope.trace.span.SpanContext;
import com.vipshop.microscope.trace.span.SpanId;
import com.vipshop.microscope.trace.switcher.Switcher;

/**
 * Tracing client API for Java.
 * 
 * <p>Basically, we use {@code collector} as our backend system,
 * we build a java tracing client to collector message, use
 * {@code ThreadTransporter} transport spans to {@code collector}.
 * 
 * <p>Application programmers can use this API in app code
 * if necessary. But in most case, we will embed this tracing API
 * to framework. 
 * 
 * <p>For example, we offer: 
 * micorscope-adapter-spring.jar to trace spring
 * micorscope-adapter-mybatis.jar to trace mybatis
 * 
 * @see micorscope-adapter-*.jar for more.
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
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
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
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
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
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		spanBuilder.setResultCode(result);
	}
	
	/**
	 * Complete a Span.
	 */
	public void clientReceive() {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		spanBuilder.clientReceive();
	}
	
	@Override
	public String toString() {
		return "SpanBuilder --> " + this.spanBuilder.toString();
	}
	
}
