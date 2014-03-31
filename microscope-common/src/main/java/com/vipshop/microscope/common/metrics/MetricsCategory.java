package com.vipshop.microscope.common.metrics;

public class MetricsCategory {
	
	// ********************** logic category *************************//
	
	/**
	 * JVM 
	 */
	public static final String JVM = "jvm";
	public static final String JVM_Thread = "jvm_thread";
	public static final String JVM_Memory = "jvm_memory";
	public static final String JVM_GC = "jvm_gc";
	public static final String JVM_Runtime = "jvm_runtime";
	public static final String JVM_OS = "jvm_os";
	
	/**
	 * Http request response
	 */
	public static final String Servlet = "servlet";

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
