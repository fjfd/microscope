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
    public static final String HTTPCLIENT = "httpclient.request.";
    public static final String HTTPCLIENT_AVAILABLE_CONN = "httpclient.connections.available";
    public static final String HTTPCLIENT_LEASED_CONN = "httpclient.connections.leased";
    public static final String HTTPCLIENT_MAX_CONN = "httpclient.connections.max";
    public static final String HTTPCLIENT_PENDING_CONN = "httpclient.connections.pending";

    /**
     * Thrift metrics category
     */
    public static final String THTTPCLIENT = "thriftclient.";

    /**
     * Servlet metrics category
     */
    public static final String SERVLET_CODE_OK           = "servlet.response-code.200";
    public static final String SERVLET_CODE_CREATED      = "servlet.response-code.201";
    public static final String SERVLET_CODE_NO_CONTENT   = "servlet.response-code.204";
    public static final String SERVLET_CODE_BAD_REQUEST  = "servlet.response-code.400";
    public static final String SERVLET_CODE_NOT_FOUND    = "servlet.response-code.404";
    public static final String SERVLET_CODE_SERVER_ERROR = "servlet.response-code.500";
    public static final String SERVLET_CODE_OTHER        = "servlet.response-code.other";

    public static final String SERVLET_ACTIVE_REQUEST    = "servlet.request.active-request";
    public static final String SERVLET_REQUEST           = "servlet.request.timer";

    public static final int OK                           = 200;
    public static final int CREATED                      = 201;
    public static final int NO_CONTENT                   = 204;
    public static final int BAD_REQUEST                  = 400;
    public static final int NOT_FOUND                    = 404;
    public static final int SERVER_ERROR                 = 500;

    /**
     * Cache metrics category
     */
    public static final String Cache = "cache.";

    /**
     * DB
     */
    public static final String DB = "db.";

}
