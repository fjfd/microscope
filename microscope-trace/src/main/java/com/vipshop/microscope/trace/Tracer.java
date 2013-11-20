package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.span.Category;

public class Tracer {
	
	/**
	 * Handle Method opeations.
	 * 
	 * @param spanName
	 * @param category
	 */
	public static void clientSend(String spanName, Category category){
		TraceFactory.getTrace().clientSend(spanName, category);
	}
	
	/**
	 * Handle mybatis operations.
	 * 
	 * @param handler
	 * @param category
	 */
	public static void clientSend(String name, String serverIP, Category category) {
		TraceFactory.getTrace().clientSend(name, serverIP, category);
	}

	/**
	 * Handle corss-jvm operations.(http url request --> spring mvc/resteasy)
	 * 
	 * @param requestContext
	 * @param category
	 */
	public static void clientSend(String traceId, String spanId, String name, Category category){
		TraceFactory.setTraceContexToThreadLocal(traceId, spanId);
		TraceFactory.getTrace().clientSend(name, category);
	}
	
	/**
	 * Set result code when exception happens.
	 * 
	 * @param e
	 */
	public static void setResultCode(String result) {
		Trace trace = TraceFactory.getContext();
		if (trace != null) {
			trace.setResutlCode(result);
		}
	}
	
	/**
	 * trace end api
	 */
	public static void clientReceive() {
		TraceFactory.getTrace().clientReceive();
	}
	
	/**
	 * app programmer api
	 * 
	 * @param key
	 * @param value
	 */
	public static void record(String key, String value) {
		Trace trace = TraceFactory.getContext();
		if (trace != null) {
			trace.record(key, value);
		}
	}
	
	/**
	 * asyn thread invoke.
	 * 
	 * @return
	 */
	public static Trace getContext() {
		return TraceFactory.getContext();
	}
	
	/**
	 * Set context
	 * 
	 * @param trace
	 */
	public static void setContext(Trace trace) {
		TraceFactory.setContext(trace);
	}
	
	/**
	 * integrate with other system.
	 * 
	 * @return
	 */
	public static long getTraceId() {
		return TraceFactory.getTraceId();
	}
	
}
