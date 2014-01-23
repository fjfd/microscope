package com.vipshop.microscope.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.util.ConfigurationUtil;
import com.vipshop.microscope.trace.switcher.ConfigSwitcher;
import com.vipshop.microscope.trace.switcher.Switcher;
import com.vipshop.microscope.trace.transport.QueueTransporter;
import com.vipshop.microscope.trace.transport.Transporter;

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
	
	private static final Logger logger = LoggerFactory.getLogger(Tracer.class);
	
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
	
	private static final Switcher SWITCHER = new ConfigSwitcher();
	private static final Transporter TRANSPORTER = new QueueTransporter();
	
	/**
	 * Start transporter.
	 */
	static {
		try {
			if (SWITCHER.isOpen()) {
				TRANSPORTER.transport();
			}
		} catch (Exception e) {
			logger.error("start thrift transporter faile, ", e.getCause());
		}
	}
	
	/**
	 * Handle common method opeations.
	 * 
	 * @param spanName the name of method
	 * @param category the category of service
	 */
	public static void clientSend(String spanName, Category category){
		try {
			if (SWITCHER.isClose()) {
				return;
			}
			TraceContext.getTrace().clientSend(spanName, category);
		} catch (Exception e) {
			logger.info("client send error", e);
		}
	}
	
	/**
	 * Handle MyBatis/Hibernate/SQL/database operations.
	 * 
	 * @param name the name of method
	 * @param serverIP the database name where sql execute
	 * @param category the category of service
	 */
	public static void clientSend(String name, String server, Category category) {
		try {
			if (SWITCHER.isClose()) {
				return;
			}
			TraceContext.getTrace().clientSend(name, server, category);
		} catch (Exception e) {
			logger.info("client send error", e);
		}
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
		try {
			if (SWITCHER.isClose()) {
				return;
			}
			TraceContext.getTrace(traceId, spanId).clientSend(name, category);
		} catch (Exception e) {
			logger.info("client send error", e);
		}
	}
	
	/**
	 * Complete a span.
	 */
	public static void clientReceive() {
		try {
			if (SWITCHER.isClose()) {
				return;
			}
			TraceContext.getTrace().clientReceive();
		} catch (Exception e) {
			logger.info("client receive error", e);
		}
	}
	
	public static void serverSend() {
		
	}
	
	public static void serverRecv() {
		
	}

	/**
	 * Set result code.
	 * 
	 * If exception happens. set ResultCode = EXCEPTION.
	 * 
	 * @param result
	 */
	public static void setResultCode(String result) {
		try {
			if (SWITCHER.isClose()) {
				return;
			}
			Trace trace = TraceContext.getContext();
			if (trace != null) {
				trace.setResutlCode(result);
			}
		} catch (Exception e) {
			logger.info("set resultcode error", e);
		}
	}
	
	/**
	 * Record info
	 * 
	 * @param info
	 */
	public static void record(String info) {
		
	}
	
	/**
	 * Record key/value
	 * 
	 * @param key
	 * @param value
	 */
	public static void record(String key, String value) {
		addDebug(key, value);
	}
	
	/**
	 * Add debug info
	 * 
	 * @param key
	 * @param value
	 */
	public static void addDebug(String key, String value) {
		try {
			if (SWITCHER.isClose()) {
				return;
			}
			Trace trace = TraceContext.getContext();
			if (trace != null) {
				trace.addDebug(key, value);
			}
		} catch (Exception e) {
			logger.info("add debug info error", e);
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
		try {
			if (SWITCHER.isClose()) {
				return null;
			}
			return TraceContext.getContext();
		} catch (Exception e) {
			return null;
		}
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
		try {
			if (SWITCHER.isClose()) {
				return;
			}
			TraceContext.setContext(trace);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Clean {@code ThreadLocal}
	 */
	public static void cleanContext() {
		try {
			if (SWITCHER.isClose()) {
				return;
			}
			TraceContext.cleanContext();
		} catch (Exception e) {
		
		}
	}
	
	/**
	 * Get traceId from {@code ThreadLocal}
	 * 
	 * @return traceId
	 */
	public static String getTraceId() {
		try {
			if (SWITCHER.isClose()) {
				return null;
			}
			return TraceContext.getTraceIdFromThreadLocal();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Get spanId from {@code ThreadLocal}
	 * 
	 * @return spanId
	 */
	public static String getSpanId() {
		try {
			if (SWITCHER.isClose()) {
				return null;
			}
			return TraceContext.getSpanIdFromThreadLocal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
