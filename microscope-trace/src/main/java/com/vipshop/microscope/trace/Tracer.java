package com.vipshop.microscope.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheck;
import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.DateUtil;
import com.vipshop.microscope.trace.exception.ExceptionBuilder;
import com.vipshop.microscope.trace.metrics.Metrics;
import com.vipshop.microscope.trace.switcher.Switcher;
import com.vipshop.microscope.trace.switcher.SwitcherHolder;
import com.vipshop.microscope.trace.transport.TransporterHolder;

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
 * @author Xu Fei
 * @version 1.0
 */
public class Tracer {
	
	private static final Logger logger = LoggerFactory.getLogger(Tracer.class);
	
	/**
	 * HTTP header for propagate trace link.
	 * 
	 * As nginx server will remove string "X_B3_Trace_Id" with
	 * underline, so use middle line "X-B3-Trace-Id".
	 */
	public static final String X_B3_TRACE_ID = "X-B3-Trace-Id";
	public static final String X_B3_SPAN_ID = "X-B3-Span-Id";
	public static final String X_B3_PARENT_ID = "X-B3-Parent-Id";
	public static final String X_B3_FLAG = "X-B3-Flag";
	public static final String X_B3_SAMPLED = "X-B3-Sampled";
	
	/**
	 * Trace result status
	 */
	public static final String OK = "OK";
	public static final String EXCEPTION = "EXCEPTION";

	/**
	 * Default app name
	 */
	public static String APP_NAME = "default-app-name";
	
	/**
	 * Default collector host
	 */
	public static String COLLECTOR_HOST = "10.19.111.64";
	
	/**
	 * Default collector port
	 */
	public static int COLLECTOR_PORT = 9410;
	
	/**
	 * Default batch send spans size
	 */
	public static int MAX_BATCH_SIZE = 100;
	
	/**
	 * Default wait null span times
	 */
	public static int MAX_EMPTY_SIZE = 100;
	
	/**
	 * Default close microscope monitor
	 */
	public static int SWITCH = 0;
	
	public static int QUEUE_SIZE = 10000;
	public static int RECONNECT_WAIT_TIME = 3000;
	public static int SEND_WAIT_TIME = 100;
	
	public static int REPORT_PERIOD_TIME = 10;
	
	private static Switcher SWITCHER = SwitcherHolder.getConfigReloadSwitcher();
	
	/**
	 * If trace.properties exist in classpath, then means application
	 * developer want to monitor by microscope. Read values from file
	 * and start transporter.
	 * 
	 * If trace.properties not exist, means DO NOT trace and do nothing.
	 */
	static {
		if (ConfigurationUtil.fileExist("trace.properties")) {
			ConfigurationUtil config = ConfigurationUtil.getConfiguration("trace.properties");
			
			APP_NAME = config.getString("app_name");
			COLLECTOR_HOST = config.getString("collector_host");
			
			COLLECTOR_PORT = config.getInt("collector_port");
			MAX_BATCH_SIZE = config.getInt("max_batch_size");
			MAX_EMPTY_SIZE = config.getInt("max_empty_size");
			SWITCH = config.getInt("switch");
			QUEUE_SIZE = config.getInt("queue_size");
			RECONNECT_WAIT_TIME = config.getInt("reconnect_wait_time");
			SEND_WAIT_TIME = config.getInt("send_wait_time");
			
			REPORT_PERIOD_TIME = config.getInt("report_period_time");
			
			try {
				if (SWITCHER.isOpen()) {
					
					/**
					 * start queue transporter
					 */
					TransporterHolder.startQueueTransporter();
					
					/**
					 * start metrics reporter 
					 */
					Metrics.startMicroscopeReporter();
					
					/**
					 * register JVM metrics
					 */
					Metrics.registerJVM();
					
				}
			} catch (Exception e) {
				SWITCH = 0;
				logger.error("start microscope client error, close client", e);
			}
		}
		
	}
	
	private Tracer() {}
	
	/**
	 * Is trace function open or close. 
	 * 
	 * @return {@code true} if trace enable, {@code false} if not 
	 */
	public static boolean isTraceEnable() {
		return SWITCHER.isOpen();
	}

	// ******* methods for client send and receive span ******* //
	
	/**
	 * Handle common method operations.
	 * 
	 * @param spanName the name of method
	 * @param category the category of service
	 */
	public static void clientSend(String spanName, Category category){
		if (SWITCHER.isClose()) 
			return;
		
		try {
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
		if (SWITCHER.isClose()) 
			return;
		
		try {
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
		if (SWITCHER.isClose()) 
			return;
		
		try {
			TraceContext.getTrace(traceId, spanId).clientSend(name, category);
		} catch (Exception e) {
			logger.info("client send error", e);
		}
	}
	
	/**
	 * Complete a span.
	 */
	public static void clientReceive() {
		if (SWITCHER.isClose()) 
			return;
		
		try {
			TraceContext.getTrace().clientReceive();
		} catch (Exception e) {
			logger.info("client receive error", e);
		}
	}
	
	// ******* methods for server send and receive span ******* //
	
	/**
	 * Server side send response.
	 * 
	 */
	public static void serverSend() {
	}
	
	/**
	 * Server side receive request.
	 */
	public static void serverRecv() {
	}
	
	// *************** set result code when exception happens ********************** //
	
	/**
	 * Set result code.
	 * 
	 * If exception happens. set ResultCode = EXCEPTION.
	 * 
	 * @param Throwable
	 */
	public static void setResultCode(Throwable t) {
		if (SWITCHER.isClose()) 
			return;
		
		try {
			Trace trace = TraceContext.getContext();
			if (trace != null) {
				trace.setResutlCode(EXCEPTION);
				record(t);
			}
		} catch (Exception e) {
			logger.info("set resultcode error", e);
		}
	}

	// ********* methods for record debug info on spans *********** // 
	
	/**
	 * Record info with current time.
	 * 
	 * key is date of current time like "2014-02-17 11:32:10"
	 * value is string with debug info like "user id is 1234"
	 * 
	 * @param info
	 */
	public static void record(String info) {
		addDebug(DateUtil.dateToString(), info);
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
		if (SWITCHER.isClose()) 
			return;
		
		try {
			Trace trace = TraceContext.getContext();
			if (trace != null) {
				trace.record(key, value);
			}
		} catch (Exception e) {
			logger.info("add debug info error", e);
		}
	}
	
	// ******** methods for new thread asynchronous call ******* //
	
	/**
	 * Asynchronous thread call.
	 * 
	 * Get trace object from {@code ThreadLocal}.
	 * 
	 * @return
	 */
	public static Trace getContext() {
		if (SWITCHER.isClose()) 
			return null;
		
		try {
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
		if (SWITCHER.isClose()) 
			return;
		
		try {
			TraceContext.setContext(trace);
		} catch (Exception e) {
			
		}
	}
	
	// ********************* method for clean current ThreadLocal ******************* //
	
	/**
	 * Clean {@code ThreadLocal}
	 */
	public static void cleanContext() {
		if (SWITCHER.isClose()) 
			return;
		
		try {
			TraceContext.cleanContext();
		} catch (Exception e) {
		
		}
	}
	
	// ******** methods for get trace id and span id from ThreadLocal  *******//
	
	/**
	 * Get traceId from {@code ThreadLocal}
	 * 
	 * @return traceId
	 */
	public static String getTraceId() {
		if (SWITCHER.isClose()) 
			return null;
		
		try {
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
		if (SWITCHER.isClose()) 
			return null;
		
		try {
			return TraceContext.getSpanIdFromThreadLocal();
		} catch (Exception e) {
			return null;
		}
	}
	
	//************************** methods for record exception ********************* //
	
	/**
	 * Record exception.
	 * 
	 * @param e
	 */
	public static void record(Throwable t) {
		if (SWITCHER.isClose()) 
			return;
		Trace trace = TraceContext.getContext();
		if (trace != null) {
			trace.setResutlCode("Exception");
		}
		ExceptionBuilder.record(t);
	}
	
	/**
	 * Record exception and debug info.
	 * 
	 * @param info
	 * @param e
	 */
	public static void record(Throwable t, String info) {
		if (SWITCHER.isClose()) 
			return;
		Trace trace = TraceContext.getContext();
		if (trace != null) {
			trace.setResutlCode("Exception");
		}
		ExceptionBuilder.record(t, info);
	}
	
	//************************** methods for record metrics ********************* //

	public static <T extends Metric> T register(String name, T metric) {
		return Metrics.register(name, metric);
	}
	
	public static void inc(String name) {
		Metrics.inc(name);
	}
	
	public static void inc(String name, long n) {
		Metrics.inc(name, n);
	}
	
	public static void dec(String name) {
		Metrics.dec(name);
	}
	
	public static void dec(String name, long n) {
		Metrics.dec(name, n);
	}
	
	public static Histogram histogram(String name) {
		return Metrics.histogram(name);
	}
	
	public static Meter meter(String name) {
		return Metrics.meter(name);
	}
	
	public static Timer timer(String name) {
		return Metrics.timer(name);
	}
	
	public static void register(String name, HealthCheck healthCheck) {
		Metrics.register(name, healthCheck);
	}
	
	public static void unregister(String name) {
		Metrics.unregister(name);
	}

}
