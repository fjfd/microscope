package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.span.SpanContext;
import com.vipshop.microscope.trace.span.SpanID;

/**
 * A context factory use for generate and deliver
 * {@code Trace} object.
 * 
 * <p>As trace is base on {@code Thread}, so use
 * {@code ThreadLocal} to propagate trace object
 * in single JVM. When trace cross JVM, use HTTP 
 * Header to propagate it. Currently, we have HTTP
 * and Thrift protocol.
 * 
 * <p>custom httpclient jar
 * 
 * <pre>
 * 	<dependency>
 *  	<groupId>org.apache.httpcomponents</groupId>
 *		<artifactId>httpclient</artifactId>
 *		<version>4.2</version>
 *		<classifier>microscope-1.1.2</classifier>
 *		<exclusions>
 *			<exclusion>
 *				<groupId>org.apache.httpcomponents</groupId>
 *				<artifactId>httpclient</artifactId>
 *			</exclusion>
 *		</exclusions>
 *	</dependency>
 * </pre>
 * 
 * <p>custom libthrift jar
 * 
 * <pre>
 * 	<dependency>
 * 		<groupId>org.apache.thrift</groupId>
 * 		<artifactId>libthrift</artifactId>
 * 		<version>0.9.0</version>
 * 		<classifier>microscope-1.1.2</classifier>
 *	</dependency>
 * </pre>
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceContext {
	
	/**
	 * A {@code ThreadLocal} object to store 
	 * {@code Trace} object by thread.
	 */
	private static final ThreadLocal<Trace> TRACE = new ThreadLocal<Trace>();
	
	/**
	 * Returns current thread's trace.
	 * 
	 * @return trace {@code Trace} Object.
	 */
	static Trace getContext() {
		return TRACE.get();
	}

	/**
	 * Set trace to new thread.
	 * 
	 * @param trace the context
	 */
	static void setContext(Trace trace) {
		TRACE.set(trace);
	}
	
	/**
	 * Clean ThreadLocal content.
	 */
	static void cleanContext() {
		TRACE.set(null);
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
		if (TRACE.get() == null) {
			Trace trace = new Trace();
			TRACE.set(trace);
		}
		return TRACE.get();
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
		/* 
		 * If this is a new trace.
		 */
		if (traceId == null || spanId == null) {
			Trace trace = new Trace();
			TRACE.set(trace);
			return TRACE.get();
		}

		/*
		 * If this is some part of exist trace.
		 */
		SpanID spanID = new SpanID();
		spanID.setTraceId(Long.valueOf(traceId));
		spanID.setSpanId(Long.valueOf(spanId));
		SpanContext context = new SpanContext(spanID);
		context.setRootSpanFlagFalse();

		Trace trace = new Trace(context);
		TRACE.set(trace);
		
		return TRACE.get();

	}

	/**
	 * Get traceId from ThreadLocal.
	 * 
	 * @return traceId
	 */
	static String getTraceIdFromThreadLocal() {
		if (TRACE.get() == null) {
			return null;
		}
		SpanID spanID = TRACE.get().getSpanID();
		return String.valueOf(spanID.getTraceId());
	}
	
	/**
	 * Get spanId from ThreadLocal.
	 * 
	 * @return spanId
	 */
	static String getSpanIdFromThreadLocal() {
		if (TRACE.get() == null) {
			return null;
		}
		SpanID spanID = TRACE.get().getSpanID();
		return String.valueOf(spanID.getSpanId());
	}
	
}