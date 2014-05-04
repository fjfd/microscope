package com.vipshop.microscope.collector.validater;

import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;

/**
 * Validate message from application.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MessageValidater {

    private static final String USER_INFO = "user_info";
    private static final String PASSPORT = "passport";
    private static final String WMS20 = "wms2.0";
    private static final String PICKET = "picket";

    private static final String JSESSIONID = "jsessionid";

    public static Span validate(Span span) {

        long traceId = span.getTraceId();
        long spanId = span.getSpanId();

        if (traceId == spanId) {

            String appName = span.getAppName();
            String traceName = span.getSpanName();

            // validate span message from user_info
            if (appName.equals(USER_INFO)) {
                validateUserInfo(span, traceName);
            }

            // validate span message from passport
            if (appName.equals(PASSPORT)) {
                validatePassport(span, traceName);
            }

            // validate span message from wms2.0
            if (appName.equals(WMS20)) {
                validateWMS20(span, traceName);
            }

            // validate span message from picket
            if (appName.equals(PICKET)) {
                validatePicket(span, traceName);
            }

        }
        return span;
    }

    private static Span validateUserInfo(Span span, String traceName) {
        if (traceName.matches(".*\\d.*")) {
            traceName = traceName.replaceAll("\\d", "");
            span.setSpanName(traceName);
        }
        return span;
    }

    private static Span validatePassport(Span span, String traceName) {
        // TODO validate in future
        return span;
    }

    private static Span validateWMS20(Span span, String traceName) {
        if (traceName.contains(JSESSIONID)) {
            int index = traceName.indexOf(JSESSIONID);
            traceName = traceName.substring(0, index - 1) + "@Controller";
            span.setSpanName(traceName);
        }
        return span;
    }

    private static Span validatePicket(Span span, String traceName) {
        // TODO validate in future
        return span;
    }

    public static MetricData validate(MetricData metric) {
        return metric;
    }

    public static SystemData validate(SystemData data) {
        return data;
    }

    public static ExceptionData validate(ExceptionData data) {
        return data;
    }

    public static String validate(String log) {
        return log;
    }

}
