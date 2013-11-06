package com.vipshop.microscope.trace;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;

import com.vipshop.microscope.trace.span.Category;

public class Tracer {
	
	/**
	 * trace start api
	 * 
	 * @param spanName
	 * @param category
	 */
	public static void clientSend(String spanName, Category category){
		TraceFactory.getTrace().clientSend(spanName, category);
	}
	
	/**
	 * For httpclient 4.2 send request
	 * 
	 * @param request
	 * @param category
	 */
	public static void clientSend(HttpUriRequest request, Category category){
		TraceFactory.getTrace().clientSend(getShortURL(request), category);
		TraceFactory.setHttpRequestHead(request);
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
	 * For httpclient 4.2 send request
	 * 
	 * @param request
	 * @param category
	 */
	public static void clientSend(HttpRequest request, Category category){
		TraceFactory.getTrace().clientSend(request.getRequestLine().getMethod(), category);
		TraceFactory.setHttpRequestHead(request);
	}
	
	/**
	 * For javax servlet receive http request
	 * 
	 * @param request
	 * @param category
	 */
	public static void clientSend(HttpServletRequest request, Category category){
		TraceFactory.getHttpRequestHead(request);
		TraceFactory.getTrace().clientSend(request.getRequestURI(), category);
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
	
	
	private static String getShortURL(HttpUriRequest request) {
		return request.getURI().getPath();
	}
	
}
