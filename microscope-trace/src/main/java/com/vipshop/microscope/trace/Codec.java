package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.gen.LogEntry;
import com.vipshop.microscope.trace.gen.Span;
import com.vipshop.microscope.trace.metrics.MetricData;
import com.vipshop.microscope.trace.metrics.SystemMetric;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * A codec for {@code LogEntry}
 *
 * @author Xu Fei
 * @version 1.0
 */
public class Codec {

    private static final TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
    private static final Base64 base64 = new Base64();


    //************************  Span to logEntry  ***************************//

    /**
     * Encode span to {@code LogEntry}.
     *
     * @param span
     * @return
     */
    public static LogEntry encodeToLogEntry(Span span) {
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
        try {
            span.write(proto);
        } catch (TException e) {
            return null;
        }
        String spanAsString = base64.encodeToString(buf.toByteArray());
        LogEntry logEntry = new LogEntry(Constants.TRACE, spanAsString);
        return logEntry;
    }

    /**
     * Decode string to {@code Span}.
     *
     * @param msg
     * @return
     */
    public static Span decodeToSpan(final String msg) {
        byte[] tmp = Base64.decodeBase64(msg);
        final ByteArrayInputStream buf = new ByteArrayInputStream(tmp);
        final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
        Span span = new Span();
        try {
            span.read(proto);
        } catch (TException e) {
            return null;
        }
        span.setResultSize(msg.length());
        return span;
    }

    //************************  Metrics to logEntry  ***************************//

    public static LogEntry encodeToLogEntry(MetricData metric) {
        byte[] bytes = SerializationUtils.serialize((Serializable) metric);
        String message = Base64.encodeBase64String(bytes);
        LogEntry logEntry = new LogEntry(Constants.METRICS, message);
        return logEntry;
    }

    public static MetricData decodeToMetric(final String msg) {
        byte[] bytes = Base64.decodeBase64(msg);
        MetricData metric = (MetricData) SerializationUtils.deserialize(bytes);
        return metric;
    }

    //************************  ExceptionInfo to logEntry  ***************************//

    public static LogEntry encodeToLogEntry(HashMap<String, Object> exceptionInfo) {
        byte[] bytes = SerializationUtils.serialize((Serializable) exceptionInfo);
        String message = Base64.encodeBase64String(bytes);
        LogEntry logEntry = new LogEntry(Constants.EXCEPTION, message);
        return logEntry;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> decodeToException(final String msg) {
        byte[] bytes = Base64.decodeBase64(msg);
        HashMap<String, Object> info = (HashMap<String, Object>) SerializationUtils.deserialize(bytes);
        return info;
    }

    //************************  SystemMetric to logEntry  ***************************//

    public static LogEntry encodeToLogEntry(SystemMetric systemInfo) {
        byte[] bytes = SerializationUtils.serialize((Serializable) systemInfo);
        String message = Base64.encodeBase64String(bytes);
        LogEntry logEntry = new LogEntry(Constants.SYSTEM, message);
        return logEntry;
    }

    public static SystemMetric decodeToSystemInfo(final String msg) {
        byte[] bytes = Base64.decodeBase64(msg);
        SystemMetric info = (SystemMetric) SerializationUtils.deserialize(bytes);
        return info;
    }

    //************************  map to string  ***************************//

    /**
     * Encode map to string.
     *
     * @param map
     * @return
     */
    public static String encodeToString(HashMap<String, Object> map) {
        byte[] bytes = SerializationUtils.serialize((Serializable) map);
        return Base64.encodeBase64String(bytes);
    }

    /**
     * Decode string to map.
     *
     * @param msg
     * @return
     */
    public static HashMap<String, Object> decodeToMap(final String msg) {
        byte[] bytes = Base64.decodeBase64(msg);
        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = (HashMap<String, Object>) SerializationUtils.deserialize(bytes);
        return map;
    }

}
