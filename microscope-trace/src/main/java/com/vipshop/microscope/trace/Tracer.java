package com.vipshop.microscope.trace;

import java.util.concurrent.ExecutorService;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.util.ConfigurationUtil;
import com.vipshop.micorscope.framework.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.switcher.Switcher;
import com.vipshop.microscope.trace.transport.ThriftTransporter;

/**
 * Trace client API for Java.
 * 
 * <p>Basically, we use {@code collector} as our backend system,
 * we build a java tracing client to collector message, use
 * {@code ThreadTransporter} transport spans to {@code collector}.
 * 
 * <p>Application programmers can use this API in app code
 * if necessary. But in most case, we will embed this tracing API
 * to framework. 
 * 
 * <p>We offer: 
 * micorscope-adapter-spring    jar to trace spring
 * micorscope-adapter-resteasy  jar to trace resteasy
 * micorscope-adapter-mybatis   jar to trace mybatis
 * micorscope-adapter-aop       jar to trace service
 * micorscope-adapter-thrift    jar to trace thrift
 * micorscope-adapter-hibernate jar to trace hibernate
 * micorscope-adapter-servlet   jar to trace servlet
 * micorscope-adapter-struts    jar to trace struts
 * micorscope-adapter-cache     jar to trace cache
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class Tracer {
	
	/**
	 * Load config data from trace.properties.
	 */
	private static final ConfigurationUtil config = ConfigurationUtil.getConfiguration("trace.properties");
	
	public static final String APP_NAME = config.getString("app_name");
	public static final String COLLECTOR_HOST = config.getString("collector_host");
	
	public static final int COLLECTOR_PORT = config.getInt("collector_port");
	public static final int MAX_BATCH_SIZE = config.getInt("max_batch_size");
	public static final int MAX_EMPTY_SIZE = config.getInt("max_empty_size");
	public static final int SWITCH = config.getInt("switch");
	public static final int QUEUE_SIZE = config.getInt("queue_size");
	public static final int RECONNECT_WAIT_TIME = config.getInt("reconnect_wait_time");
	public static final int SEND_WAIT_TIME = config.getInt("send_wait_time");

	/**
	 * Start transporter.
	 */
	static {
		if (Switcher.isOpen()) {
			ExecutorService executor = ThreadPoolUtil.newSingleDaemonThreadExecutor("transporter-pool");
			executor.execute(new ThriftTransporter());
		}
	}
	
	/**
	 * Handle common method opeations.
	 * 
	 * @param spanName the name of method
	 * @param category the category of service
	 */
	public static void clientSend(String spanName, Category category){
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		TraceContext.getTrace().clientSend(spanName, category);
	}
	
	/**
	 * Handle MyBatis/Hibernate/SQL/database operations.
	 * 
	 * @param name the name of method
	 * @param serverIP the database name where sql execute
	 * @param category the category of service
	 */
	public static void clientSend(String name, String server, Category category) {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		TraceContext.getTrace().clientSend(name, server, category);
	}

	/**
	 * Handle corss-JVM operations.(httpclient request)
	 * 
	 * @param traceId the traceId from client
	 * @param spanId  the spanId from client
	 * @param name the name of method
	 * @param category the category of service
	 */
	public static void clientSend(String traceId, String spanId, String name, Category category){
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		TraceContext.getTrace(traceId, spanId).clientSend(name, category);
	}
	
	/**
	 * Complete a span.
	 */
	public static void clientReceive() {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		TraceContext.getTrace().clientReceive();
	}

	/**
	 * Set result code.
	 * 
	 * If exception happens. set ResultCode = EXCEPTION.
	 * 
	 * @param e
	 */
	public static void setResultCode(String result) {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		Trace trace = TraceContext.getContext();
		if (trace != null) {
			trace.setResutlCode(result);
		}
	}
	
	/**
	 * Add debug info
	 * 
	 * @param key
	 * @param value
	 */
	public static void addDebug(String key, String value) {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		Trace trace = TraceContext.getContext();
		if (trace != null) {
			trace.addDebug(key, value);
		}
	}
	
	/**
	 * Async thread call.
	 * 
	 * Get trace object from {@code ThreadLocal}.
	 * 
	 * @return
	 */
	public static Trace getContext() {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return null;
		}
		return TraceContext.getContext();
	}
	
	/**
	 * Asyn thread invoke.
	 * 
	 * Set trace object to {@code ThreadLocal}
	 * with the new Thread.
	 * 
	 * @param trace
	 */
	public static void setContext(Trace trace) {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		TraceContext.setContext(trace);
	}
	
	/**
	 * Clean {@code ThreadLocal}
	 */
	public static void cleanContext() {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return;
		}
		TraceContext.cleanContext();
	}
	
	/**
	 * Get traceId from {@code ThreadLocal}
	 * 
	 * @return traceId
	 */
	public static String getTraceId() {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return null;
		}
		return TraceContext.getTraceIdFromThreadLocal();
	}
	
	/**
	 * Get spanId from {@code ThreadLocal}
	 * 
	 * @return spanId
	 */
	public static String getSpanId() {
		/**
		 * if turn off tracing function, 
		 * then return immediate.
		 */
		if (Switcher.isClose()) {
			return null;
		}
		return TraceContext.getSpanIdFromThreadLocal();
	}
	
}
