package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.span.Category;

public class Tracer {
	
	public static void clientSend(String spanName){
		TraceFactory.getTrace().clientSend(spanName);
	}
	
	public static void clientSend(String spanName, Enum<Category> category){
		TraceFactory.getTrace().clientSend(spanName, category);
	}
	
	public static void clientReceive() {
		TraceFactory.getTrace().clientReceive();
	}
}
