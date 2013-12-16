package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.span.SpanContext;
import com.vipshop.microscope.trace.span.SpanId;
import com.vipshop.microscope.trace.transport.ThriftTransporter;

/**
 * A factory use for generate {@code Trace} object.
 * 
 * <p>As trace is base on {@code Thread}, so we use
 * {@code ThreadLocal} to propagate trace object. 
 * 
 * <p>When RPC invoke happens, the trace process will 
 * corss-JVM, we use HTTP Header to propagate it.
 * 
 * <p>Currently, we have HTTP/Thrift protocol.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceFactory {
	
	/**
	 * Start {@code ThreadTransporter thread}.
	 * 
	 * Use a daemon thread send spans to collector.
	 */
	static {
		ThriftTransporter.start();
	}

	/**
	 * A {@code ThreadLocal} object to store 
	 * {@code Trace} object by thread.
	 */
	private static final ThreadLocal<Trace> TRACE_CONTEXT = new ThreadLocal<Trace>();
	
	/**
	 * Returns current thread's trace.
	 * 
	 * @return trace {@code Trace} Object.
	 */
	static Trace getContext() {
		return TRACE_CONTEXT.get();
	}

	/**
	 * Set trace to new thread.
	 * 
	 * @param trace the context
	 */
	static void setContext(Trace trace) {
		TRACE_CONTEXT.set(trace);
	}
	
	/**
	 * Clean ThreadLocal content.
	 */
	static void cleanContext() {
		TRACE_CONTEXT.set(null);
	}
	
	/**
	 * Returns trace object when in single-JVM.
	 * 
	 * If current thread don't have trace object,
	 * create a new trace object, and store it in
	 * {@code ThreadLocal}.
	 * 
	 * @return {@code Trace}
	 */
	static Trace getTrace() {
		if (TRACE_CONTEXT.get() == null) {
			Trace trace = new Trace();
			TRACE_CONTEXT.set(trace);
		}
		return TRACE_CONTEXT.get();
	}
	
	/**
	 * Return trace object when cross-JVM.
	 * 
	 * If client program send a PRC request,
	 * we propagate {@code traceId} and {@code spanId}
	 * with the request, and create a new trace object
	 * base on the traceId and spanId.
	 * 
	 * @param traceId the traceId of Span.
	 * @param spanId  the spanId of Span.
	 * @return
	 */
	static Trace getTrace(String traceId, String spanId) {
		// If this is a new trace.
		if (traceId == null || spanId == null) {
			Trace trace = new Trace();
			TRACE_CONTEXT.set(trace);
			return TRACE_CONTEXT.get();
		}

		// If this is some part of exist trace.
		SpanId spanID = new SpanId();
		spanID.setTraceId(Long.valueOf(traceId));
		spanID.setSpanId(Long.valueOf(spanId));

		SpanContext context = new SpanContext(spanID);
		context.setRootSpanFlagFalse();

		Trace trace = new Trace(context);

		TRACE_CONTEXT.set(trace);
		
		return TRACE_CONTEXT.get();

	}

	/**
	 * Get traceId from ThreadLocal.
	 * 
	 * @return traceId
	 */
	static String getTraceIdFromThreadLocal() {
		if (TRACE_CONTEXT.get() == null) {
			return null;
		}
		SpanId spanID = TRACE_CONTEXT.get().getSpanId();
		return String.valueOf(spanID.getTraceId());
	}
	
	/**
	 * Get spanId from ThreadLocal.
	 * 
	 * @return spanId
	 */
	static String getSpanIdFromThreadLocal() {
		if (TRACE_CONTEXT.get() == null) {
			return null;
		}
		SpanId spanID = TRACE_CONTEXT.get().getSpanId();
		return String.valueOf(spanID.getSpanId());
	}
	
	@Override
	public String toString() {
		return "TRACE CONTEXT : " + TRACE_CONTEXT.get().toString();
	}
	
}
