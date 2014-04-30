package com.vipshop.microscope.trace;

import com.codahale.metrics.*;
import com.codahale.metrics.health.HealthCheck;
import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.DateUtil;
import com.vipshop.microscope.trace.exception.ExceptionBuilder;
import com.vipshop.microscope.trace.metrics.Metrics;
import com.vipshop.microscope.trace.span.Category;
import com.vipshop.microscope.trace.switcher.Switcher;
import com.vipshop.microscope.trace.switcher.SwitcherHolder;
import com.vipshop.microscope.trace.transport.TransporterHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trace client API for Java.
 * <p/>
 * <p>Basically, we use {@code collector} as our backend system,
 * we build a java tracing client to collector message, use
 * {@code ThreadTransporter} transport spans to {@code collector}.
 * <p/>
 * <p>Application programmers can use this API in app code
 * if necessary. But in most case, we will embed this tracing API
 * to framework.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class Tracer {

    /**
     * HTTP header for propagate trace link.
     * <p/>
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
    private static final Logger logger = LoggerFactory.getLogger(Tracer.class);
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
     * Default retry times when no data come
     */
    public static int MAX_EMPTY_SIZE = 100;

    /**
     * Default close trace function
     */
    public static int TRACE_SWITCH = 0;

    /**
     * Default close metric function
     */
    public static int METRIC_SWITCH = 0;

    /**
     * Default client queue size
     */
    public static int QUEUE_SIZE = 10000;

    /**
     * Default reconnect time for thrift client
     */
    public static int RECONNECT_WAIT_TIME = 3000;

    /**
     * Default wait time for transporter thread
     */
    public static int SEND_WAIT_TIME = 100;

    /**
     * Default period time for metrics reporter
     */
    public static int REPORT_PERIOD_TIME = 10;

    /**
     * Default storage type:
     * <p/>
     * 1 ArrayBlockingQueueStorage
     * <p/>
     * 2 DisruptorQueueStorage
     * <p/>
     * 3 Log4j2FileStorage
     */
    public static int DEFAULT_STORAGE = 1;

    /**
     * Default sampler type:
     * <p/>
     * 1 All sampler
     * <p/>
     * 2 Fixed sampler (10%)
     * <p/>
     * 3 Adapted sampler
     */
    public static int DEFAULT_SAMPLER = 1;

    /**
     * Default switcher for open/close trace function
     */
    private static Switcher SWITCHER = SwitcherHolder.getConfigSwitcher();

    /**
     * If trace.properties exist in classpath, then means application
     * developer want to monitor by microscope. Read values from file
     * and start transporter and metrics reporter.
     *
     * If trace.properties not exist,
     * means DO NOT trace and do nothing.
     */
    static {

        if (ConfigurationUtil.fileExist("trace.properties")) {

            ConfigurationUtil config = ConfigurationUtil.getConfiguration("trace.properties");

            APP_NAME = config.getString("app_name");

            COLLECTOR_HOST = config.getString("collector_host");
            COLLECTOR_PORT = config.getInt("collector_port");

            MAX_BATCH_SIZE = config.getInt("max_batch_size");
            MAX_EMPTY_SIZE = config.getInt("max_empty_size");

            TRACE_SWITCH = config.getInt("trace_switch");
            METRIC_SWITCH = config.getInt("metric_switch");

            QUEUE_SIZE = config.getInt("queue_size");
            RECONNECT_WAIT_TIME = config.getInt("reconnect_wait_time");
            SEND_WAIT_TIME = config.getInt("send_wait_time");

            REPORT_PERIOD_TIME = config.getInt("report_period_time");

            DEFAULT_STORAGE = config.getInt("storage_type");

            DEFAULT_SAMPLER = config.getInt("sampler_type");

            try {

                /**
                 * start message transporter
                 */
                if (SWITCHER.isTraceOpen()) {
                    TransporterHolder.startTransporter();
                }

                /**
                 * start metrics reporter
                 */
                if (SWITCHER.isMetricOpen()) {
                    Metrics.startMicroscopeReporter();
                }

            } catch (Exception e) {
                SWITCHER.closeTrace();
                SWITCHER.closeMetric();
                logger.error("start microscope client error, close client", e);
            }
        }

    }

    /**
     * Can't be constructed
     */
    private Tracer() {
    }

    /**
     * Is trace function open or close.
     *
     * @return {@code true} if trace enable, {@code false} if not
     */
    public static boolean isTraceEnable() {
        return SWITCHER.isTraceOpen();
    }

    /**
     * Is metric function open or close.
     *
     * @return {@code true} if metric enable, {@code false} if not.
     */
    public static boolean isMetricEnable() {
        return SWITCHER.isMetricOpen();
    }

    // ******* methods for client send and receive span ******* //

    /**
     * Handle method start operations.
     *
     * @param spanName the name of method
     * @param category the category of service
     */
    public static void clientSend(String spanName, Category category) {
        if (!isTraceEnable())
            return;

        try {
            TraceContext.getTrace().clientSend(spanName, category);
        } catch (Exception e) {
            logger.info("client send error", e);
        }
    }

    /**
     * DO NOT use this method anymore.
     * <p/>
     * Handle method start operations.
     *
     * @param name     the name of method
     * @param server   the database name where sql execute
     * @param category the category of service
     * @since 2014/4/30
     */
    @Deprecated
    public static void clientSend(String name, String server, Category category) {
        if (!isTraceEnable())
            return;

        try {
            TraceContext.getTrace().clientSend(name, server, category);
        } catch (Exception e) {
            logger.info("client send error", e);
        }
    }

    /**
     * Handle cross-JVM method start operations.
     *
     * @param traceId  the traceId from client
     * @param spanId   the spanId from client
     * @param name     the name of method
     * @param category the category of service
     */
    public static void clientSend(String traceId, String spanId, String name, Category category) {
        if (!isTraceEnable())
            return;

        try {
            TraceContext.getTrace(traceId, spanId).clientSend(name, category);
        } catch (Exception e) {
            logger.info("cross-JVM client send error", e);
        }
    }

    /**
     * Complete a method.
     */
    public static void clientReceive() {
        if (!isTraceEnable())
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
     * <p/>
     * If exception happens. set ResultCode = EXCEPTION.
     *
     * @param t
     */
    public static void setResultCode(Throwable t) {
        if (!isTraceEnable())
            return;

        try {
            Trace trace = TraceContext.getContext();
            if (trace != null) {
                trace.setResutlCode(EXCEPTION);
                recordException(t);
            }
        } catch (Exception e) {
            logger.info("set result code error", e);
        }
    }

    // ********* methods for recordException debug info on spans *********** //

    /**
     * Record info with current time.
     * <p/>
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
        if (!isTraceEnable())
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
     * <p/>
     * Get trace object from {@code ThreadLocal}.
     *
     * @return
     */
    public static Trace getContext() {
        if (!isTraceEnable())
            return null;

        try {
            return TraceContext.getContext();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Asyn thread invoke.
     * <p/>
     * Set trace object to {@code ThreadLocal}
     * with the new Thread.
     *
     * @param trace
     */
    public static void setContext(Trace trace) {
        if (!isTraceEnable())
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
        if (!isTraceEnable())
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
        if (!isTraceEnable())
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
        if (!isTraceEnable())
            return null;

        try {
            return TraceContext.getSpanIdFromThreadLocal();
        } catch (Exception e) {
            return null;
        }
    }

    //************************** methods for recordException ********************* //

    /**
     * Record exception.
     *
     * @param t
     */
    public static void recordException(Throwable t) {
        if (!isTraceEnable())
            return;

        Trace trace = TraceContext.getContext();
        if (trace != null) {
            trace.setResutlCode(EXCEPTION);
        }
        ExceptionBuilder.record(t);
    }

    /**
     * Record exception and debug info.
     *
     * @param t
     * @param info
     */
    public static void recordException(Throwable t, String info) {
        if (!isTraceEnable())
            return;

        Trace trace = TraceContext.getContext();
        if (trace != null) {
            trace.setResutlCode(EXCEPTION);
        }
        ExceptionBuilder.record(t, info);
    }

    //************************** methods for record metrics ********************* //

    /**
     * Given a {@link Metric}, registers it under the given name.
     *
     * @param name   the name of the metric
     * @param metric the metric
     * @param <T>    the type of the metric
     * @throws IllegalArgumentException if the name is already registered
     */
    public static <T extends Metric> void register(String name, T metric) {
        if (!isMetricEnable())
            return;

        Metrics.register(name, metric);
    }

    /**
     * Increment the counter by one.
     *
     * @param name the counter name
     */
    public static void inc(String name) {
        if (!isMetricEnable())
            return;

        Metrics.inc(name);
    }

    /**
     * Increment the counter by {@code n}
     *
     * @param name the counter name
     * @param n    the increment
     */
    public static void inc(String name, long n) {
        if (!isMetricEnable())
            return;

        Metrics.inc(name, n);
    }

    /**
     * Decrement the counter by one.
     *
     * @param name counter name
     */
    public static void dec(String name) {
        if (!isMetricEnable())
            return;

        Metrics.dec(name);
    }

    /**
     * Decrement the counter by n.
     *
     * @param name counter name
     * @param n    decrement
     */
    public static void dec(String name, long n) {
        if (!isMetricEnable())
            return;

        Metrics.dec(name, n);
    }

    /**
     * Creates a new {@link com.codahale.metrics.Counter} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link com.codahale.metrics.Counter}
     */
    public static Counter counter(String name) {
        if (!isMetricEnable())
            return null;

        return Metrics.counter(name);
    }

    /**
     * Creates a new {@link Histogram} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link Histogram}
     */
    public static Histogram histogram(String name) {
        return Metrics.histogram(name);
    }

    /**
     * Creates a new {@link Timer} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link Timer}
     */
    public static Meter meter(String name) {
        return Metrics.meter(name);
    }

    /**
     * Creates a new {@link Timer} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link Timer}
     */
    public static Timer timer(String name) {
        return Metrics.timer(name);
    }

    /**
     * Registers an application {@link HealthCheck}.
     *
     * @param name        the name of the health check
     * @param healthCheck the {@link HealthCheck} instance
     */
    public static void register(String name, HealthCheck healthCheck) {
        Metrics.register(name, healthCheck);
    }

    /**
     * Unregisters the application {@link HealthCheck} with the given name.
     *
     * @param name the name of the {@link HealthCheck} instance
     */
    public static void unregister(String name) {
        Metrics.unregister(name);
    }

}
