package com.vipshop.microscope.trace;

import java.util.concurrent.ExecutorService;

import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.span.SpanContext;
import com.vipshop.microscope.trace.span.SpanId;
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
	 * Returns current trace id.
	 * 
	 * Integrate with exception monitoring.
	 * 
	 * @return current trace id.
	 */
	public static long getTraceId() {
		if (TRACE_CONTEXT.get() == null) {
			return 0L;
		}
		return TRACE_CONTEXT.get().getSpanId().getTraceId();
	}
	
	/*
	 * when application start a new thread, 
	 * programmer should propagate the contxt.
	 * 
	 */
	
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
	 * Returns trace object.
	 * 
	 * If current thread don't have trace object,
	 * then create a new one.
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
	 * Returns traceId from ThreadLocal.
	 * 
	 * @return
	 */
	public static long getTraceIdFromThreadLocal() {
		SpanId spanID = TRACE_CONTEXT.get().getSpanId();
		return spanID.getTraceId();
	}
	
	/**
	 * Returns spanId from ThreadLocal.
	 * 
	 * @return
	 */
	public static long getSpanIdFromThreadLocal() {
		SpanId spanID = TRACE_CONTEXT.get().getSpanId();
		return spanID.getSpanId();
	}
	
	/**
	 * Set traceId and spanId to ThreadLocal
	 * 
	 * from HTTPHeader OR Thrift
	 * 
	 * @param request
	 */
	public static void setTraceContexToThreadLocal(String traceId, String spanId) {
		// If this is a new trace.
		if (traceId == null || spanId == null) {
			Trace trace = new Trace();
			TRACE_CONTEXT.set(trace);
			return;
		}

		// If this is some part of exist trace.
		SpanId spanID = new SpanId();
		spanID.setTraceId(Long.valueOf(traceId));
		spanID.setSpanId(Long.valueOf(spanId));

		SpanContext context = new SpanContext(spanID);
		context.setRootSpanFlagFalse();

		Trace trace = new Trace(context);

		TRACE_CONTEXT.set(trace);
	}

	public static void cleanContext() {
		TRACE_CONTEXT.set(null);
	}
	
	@Override
	public String toString() {
		return "TRACE CONTEXT : " + TRACE_CONTEXT.get().toString();
	}
	
}
