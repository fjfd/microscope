package com.vipshop.microscope.common.metrics;

public class MetricsCategory {
	
	// ********************** logic category *************************//
	
	/**
	 * JVM 
	 */
	public static final String JVM = "jvm";
	public static final String JVM_Overview = "jvm_overview";
	public static final String JVM_Runtime = "jvm_runtime";
	public static final String JVM_Thread = "jvm_thread";
	public static final String JVM_Memory = "jvm_memory";
	public static final String JVM_GC = "jvm_gc";
	
	/**
	 * Http request response
	 */
	public static final String HttpClient = "httpclient";
	public static final String Servlet = "servlet";
	
	public static final String Servlet_Active_Request = "servlet-activeRequests";
	public static final String Servlet_Response_Code = "servlet-responseCodes-";
	public static final String Servlet_Request = "servlet-requests";
	
	public static final String Http_Connection = "http-connection";

	/**
	 * Cache 
	 */
	public static final String Cache = "cache";
	
	/**
	 * DB
	 */
	public static final String DB = "db";
	
	
	// ********************** basic category *************************//
	
	/** 
	 * exception metrics
	 */
	public static final String Exception = "exception";
	
	/**
	 * health metrics
	 */
	public static final String Health = "health";

	/**
	 * schedule metrics
	 */
	public static final String Counter = "counter";
	public static final String Gauge = "gauge";
	public static final String Histogram = "histogram";
	public static final String Meter = "meter";
	public static final String Timer = "timer";

}
