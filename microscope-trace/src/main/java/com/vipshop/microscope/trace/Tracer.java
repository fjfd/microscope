package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.span.Category;

public class Tracer {
	
	public static void clientSend(String spanName, Category category){
		TraceFactory.getTrace().clientSend(spanName, category);
	}
	
	public static void clientReceive() {
		TraceFactory.getTrace().clientReceive();
	}
	
	public static void record(String key, String value) {
		TraceFactory.getTrace().record(key, value);
	}
	
	public static Trace getContext() {
		return TraceFactory.getContext();
	}
	
	public static void setContext(Trace trace) {
		TraceFactory.setContext(trace);
	}
	
	public static long getTraceId() {
		return TraceFactory.getTraceId();
	}
	
}
