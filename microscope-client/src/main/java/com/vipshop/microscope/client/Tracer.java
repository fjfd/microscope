package com.vipshop.microscope.client;

import com.codahale.metrics.*;
import com.codahale.metrics.health.HealthCheck;
import com.vipshop.microscope.client.configurator.Configurator;
import com.vipshop.microscope.client.configurator.Configurators;
import com.vipshop.microscope.client.exception.Exceptions;
import com.vipshop.microscope.client.metric.Metrics;
import com.vipshop.microscope.client.system.SystemDatas;
import com.vipshop.microscope.client.trace.SpanCategory;
import com.vipshop.microscope.client.trace.Trace;
import com.vipshop.microscope.client.trace.TraceContext;
import com.vipshop.microscope.client.trace.TraceStatus;
import com.vipshop.microscope.client.transporter.Transporters;
import com.vipshop.microscope.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Microscope client API for Java.
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
     * Default app name
     */
    public static String APP_NAME = Configurators.DEFAULT_APP_NAME;

    /**
     * Default collector host
     */
    public static String COLLECTOR_HOST = Configurators.DEFAULT_COLLECTOR_HOST;

    /**
     * Default collector port
     */
    public static int COLLECTOR_PORT = Configurators.DEFAULT_COLLECTOR_PORT;

    /**
     * Default batch send spans size
     */
    public static int MAX_BATCH_SIZE = Configurators.DEFAULT_MAX_BATCH_SIZE;

    /**
     * Default retry times when no data come
     */
    public static int MAX_EMPTY_SIZE = Configurators.DEFAULT_MAX_EMPTY_SIZE;

    /**
     * Default close trace function
     */
    public static volatile int TRACE_SWITCH = Configurators.DEFAULT_TRACE_SWITCH;

    /**
     * Default close metric function
     */
    public static volatile int METRIC_SWITCH = Configurators.DEFAULT_METRIC_SWITCH;

    /**
     * Default close system function
     */
    public static volatile int SYSTEM_SWITCH = Configurators.DEFAULT_SYSTEM_SWITCH;

    /**
     * Default close exception function
     */
    public static volatile int EXCEPTION_SWITCH = Configurators.DEFAULT_EXCEPTION_SWITCH;

    /**
     * Default close transport function
     */
    public static volatile int TRANSPORT_SWITCH = Configurators.DEFAULT_TRANSPORT_SWITCH;

    /**
     * Default client queue size
     */
    public static int QUEUE_SIZE = Configurators.DEFAULT_QUEUE_SIZE;

    /**
     * Default reconnect time for thrift client
     */
    public static int RECONNECT_WAIT_TIME = Configurators.DEFAULT_RECONNECT_WAIT_TIME;

    /**
     * Default wait time for transporter thread
     */
    public static int SEND_WAIT_TIME = Configurators.DEFAULT_SEND_WAIT_TIME;

    /**
     * Default period time for metrics reporter
     */
    public static int REPORT_PERIOD_TIME = Configurators.DEFAULT_REPORT_PERIOD_TIME;

    /**
     * Use {@code ArrayBlockingQueueStorage} as default storage
     */
    public static int STORAGE_TYPE = Configurators.DEFAULT_STORAGE_TYPE;

    /**
     * Use {@code AllSampler} as default sampler
     */
    public static int SAMPLER_TYPE = Configurators.DEFAULT_SAMPLER_TYPE;

    /**
     * Config properties file name
     */
    private static final String CONFIG_NAME = Configurators.DEFAULT_CONFIG_NAME;

    /**
     * Config properties reader
     */
    private static Configurator config;

    /**
     * If microscope.properties exist in classpath, then means application
     * developer want to monitor by microscope. Read values from file
     * and start transporter and metrics reporter.
     *
     * If trace.properties not exist,
     * means DO NOT trace and do nothing.
     */
    static {

        if (Configurators.hasConfigFile(CONFIG_NAME)) {

            config = Configurators.getConfig(CONFIG_NAME);

            APP_NAME = config.getAppName();

            COLLECTOR_HOST = config.getCollectorHost();
            COLLECTOR_PORT = config.getCollectorPort();

            MAX_BATCH_SIZE = config.getMaxBatchSize();
            MAX_EMPTY_SIZE = config.getMaxEmptySize();

            TRACE_SWITCH = config.getTraceSwitch();
            METRIC_SWITCH = config.getMetricSwitch();
            SYSTEM_SWITCH = config.getSystemSwitch();
            EXCEPTION_SWITCH = config.getExceptionSwitch();
            TRANSPORT_SWITCH = config.getTraceSwitch();

            QUEUE_SIZE = config.getQueueSize();
            RECONNECT_WAIT_TIME = config.getReconnectWaitTime();
            SEND_WAIT_TIME = config.getSendWaitTime();

            REPORT_PERIOD_TIME = config.getReportPeriodTime();

            STORAGE_TYPE = config.getStorageType();
            SAMPLER_TYPE = config.getSamplerType();

            try {

                /**
                 * start message transporter
                 */
                if (config.isTransportOpen()) {
                    Transporters.startTransporter();
                }

                /**
                 * start metrics reporter
                 */
                if (config.isMetricOpen()) {
                    Metrics.startMicroscopeReporter();
                }

                /**
                 * record system data
                 */
                if (config.isSystemOpen()){
                    SystemDatas.record();
                }


            } catch (Exception e) {
                config.closeTrace();
                config.closeMetric();
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
        return config.isTraceOpen();
    }

    /**
     * Is metric function open or close.
     *
     * @return {@code true} if metric enable, {@code false} if not.
     */
    public static boolean isMetricEnable() {
        return config.isMetricOpen();
    }

    public static boolean isExceptionEnable() {
        return config.isExceptionOpen();
    }

    /**
     * Handle method start operations.
     *
     * @param spanName the name of method
     * @param category the category of service
     */
    public static void clientSend(String spanName, SpanCategory category) {
        if (!isTraceEnable())
            return;

        try {
            TraceContext.getTrace().clientSend(spanName, category);
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
    public static void clientSend(String traceId, String spanId, String name, SpanCategory category) {
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

    /**
     * Server side send response.
     */
    public static void serverSend() {
        throw new UnsupportedOperationException();
    }

    /**
     * Server side receive request.
     */
    public static void serverRecv() {
        throw new UnsupportedOperationException();
    }

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
                trace.setResutlCode(TraceStatus.EXCEPTION);
                Exceptions.record(t);
            }
        } catch (Exception e) {
            logger.info("set result code error", e);
        }
    }

    /**
     * Record info with current time.
     * <p/>
     * key is date of current time like "2014-02-17 11:32:10"
     * value is string with debug info like "user id is 1234"
     *
     * @param info
     */
    public static void record(String info) {
        if (!isTraceEnable())
            return;

        try {
            Trace trace = TraceContext.getContext();
            if (trace != null) {
                trace.record(DateUtil.dateToString(), info);
            }
        } catch (Exception e) {
            logger.info("add debug info error", e);
        }
    }

    /**
     * Record key/value
     *
     * @param key
     * @param value
     */
    public static void record(String key, String value) {
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
     * Async thread invoke.
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

    /**
     * Record exception data.
     *
     * @param t
     */
    public static void recordException(Throwable t) {
        if (!isExceptionEnable())
            return;

        Trace trace = TraceContext.getContext();
        if (trace != null) {
            trace.setResutlCode(TraceStatus.EXCEPTION);
        }
        Exceptions.record(t);
    }

    /**
     * Record exception and debug info.
     *
     * @param t
     * @param info
     */
    public static void recordException(Throwable t, String info) {
        if (!isExceptionEnable())
            return;

        Trace trace = TraceContext.getContext();
        if (trace != null) {
            trace.setResutlCode(TraceStatus.EXCEPTION);
        }
        Exceptions.record(t, info);
    }

    /**
     * Given a {@link com.codahale.metrics.Metric}, registers it under the given name.
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
     * Creates a new {@link com.codahale.metrics.Histogram} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link com.codahale.metrics.Histogram}
     */
    public static Histogram histogram(String name) {
        return Metrics.histogram(name);
    }

    /**
     * Creates a new {@link com.codahale.metrics.Timer} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link com.codahale.metrics.Timer}
     */
    public static Meter meter(String name) {
        return Metrics.meter(name);
    }

    /**
     * Creates a new {@link com.codahale.metrics.Timer} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link com.codahale.metrics.Timer}
     */
    public static Timer timer(String name) {
        return Metrics.timer(name);
    }

    /**
     * Registers an application {@link com.codahale.metrics.health.HealthCheck}.
     *
     * @param name        the name of the health check
     * @param healthCheck the {@link com.codahale.metrics.health.HealthCheck} instance
     */
    public static void register(String name, HealthCheck healthCheck) {
        Metrics.register(name, healthCheck);
    }

    /**
     * Unregisters the application {@link com.codahale.metrics.health.HealthCheck} with the given name.
     *
     * @param name the name of the {@link com.codahale.metrics.health.HealthCheck} instance
     */
    public static void unregister(String name) {
        Metrics.unregister(name);
    }

}
