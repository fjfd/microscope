package com.vipshop.microscope.common.logentry;

public class Constants {

    /**
     * Common constant
     */
    public static final String APP = "APP";
    public static final String IP = "IP";
    public static final String DATE = "Date";
    public static final String NAME = "Name";
    public static final String STARTTIME = "startTime";
    public static final String ENDTIME = "endTime";
    public static final String LIMIT = "limit";

    /**
     * Time serious
     */
    public static final short MAX_TIMESPAN = 3600;

    /**
     * LogEntry category
     */
    public static final String TRACE = "trace";
    public static final String METRICS = "metrics";
    public static final String EXCEPTION = "exception";
    public static final String SYSTEM = "system";

    /**
     * Exception constant
     */
    public static final String EXCEPTION_NAME = "Name";
    public static final String EXCEPTION_MESSAGE = "Message";
    public static final String EXCEPTION_STACK = "Stack";
    public static final String TRACE_ID = "TraceId";
    public static final String THREAD_INFO = "Thread";
    public static final String DEBUG = "Debug";

    /**
     * JVM metrics category
     */
    public static final String JVM = "jvm";
    public static final String JVM_OVERVIEW = "jvm.overview";
    public static final String JVM_MONITOR = "jvm.monitor";
    public static final String JVM_THREAD = "jvm.thread";
    public static final String JVM_MEMORY = "jvm.memory";
    public static final String JVM_GC = "jvm.gc";

    /**
     * Http metrics category
     */
    public static final String HTTPCLIENT = "httpclient.";
    public static final String HTTPCLIENT_AVAILABLE_CONN = "httpclient.available-connections";
    public static final String HTTPCLIENT_LEASED_CONN = "httpclient.leased-connections";
    public static final String HTTPCLIENT_MAX_CONN = "httpclient.max-connections";
    public static final String HTTPCLIENT_PENDING_CONN = "httpclient.pending-connections";

    /**
     * Thrift metrics category
     */
    public static final String THTTPCLIENT = "thriftclient.";

    /**
     * Servlet metrics category
     */
    public static final String SERVLET_CODE = "servlet.code-";
    public static final String SERVLET_OTHER_CODE = "servlet.code-other";
    public static final String SERVLET_ACTIVE_REQUEST = "servlet.active-request";
    public static final String SERVLET_REQUEST = "servlet.request";

    /**
     * Cache metrics category
     */
    public static final String Cache = "cache.";

    /**
     * DB
     */
    public static final String DB = "db.";

}
