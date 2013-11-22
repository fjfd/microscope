package com.vipshop.microscope.trace;

import java.util.concurrent.ExecutorService;

import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.span.SpanContext;
import com.vipshop.microscope.trace.span.SpanId;
import com.vipshop.microscope.trace.swith.Switch;
import com.vipshop.microscope.trace.transport.ThreadTransporter;

/**
 * A factory which generate {@code Trace} object.
 * 
 * <p>In single JVM, we use {@code ThreadLocal} to propagate trace object;
 * when things come to cross-multiple JVM, we use HTTP Header to propagate it.
 * 
 * <p>For other protocol such as {@code Thrift},same with http.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceFactory {
	
	/**
	 * Start {@code ThreadTransporter}.
	 * Use java thread send message to collector.
	 */
	static {
		if (Switch.isOpen()) {
			ExecutorService executor = ThreadPoolUtil.newSingleDaemonThreadExecutor("transporter-pool");
			executor.execute(new ThreadTransporter());
		}
	}

	/**
	 * A {@code ThreadLocal} object to store 
	 * {@code Trace} object by current thread.
	 */
	private static final ThreadLocal<Trace> TRACE_CONTEXT = new ThreadLocal<Trace>();
	
	/**
	 * Returns current thread's trace.
	 * 
	 * @return trace
	 */
	public static Trace getContext() {
		return TRACE_CONTEXT.get();
	}

	/**
	 * Set trace to new thread.
	 * 
	 * @param trace the context
	 */
	public static void setContext(Trace trace) {
		TRACE_CONTEXT.set(trace);
	}
	
	/**
	 * Clean ThreadLocal
	 */
	public static void cleanContext() {
		TRACE_CONTEXT.set(null);
	}
	
	/**
	 * Returns trace object when in-JVM.
	 * 
	 * If current thread don't have trace object,
	 * create a new trace object, and store it in
	 * {@code ThreadLocal}.
	 * 
	 * @return {@code Trace}
	 */
	public static Trace getTrace() {
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
	 * according to {@code traceId} and {@code spanId}
	 * 
	 * @param traceId 
	 * @param spanId
	 * @return
	 */
	public static Trace getTrace(String traceId, String spanId) {
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
	 * Returns traceId from ThreadLocal.
	 * 
	 * @return
	 */
	public static String getTraceIdFromThreadLocal() {
		if (TRACE_CONTEXT.get() == null) {
			return null;
		}
		SpanId spanID = TRACE_CONTEXT.get().getSpanId();
		return String.valueOf(spanID.getTraceId());
	}
	
	/**
	 * Returns spanId from ThreadLocal.
	 * 
	 * @return
	 */
	public static String getSpanIdFromThreadLocal() {
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
