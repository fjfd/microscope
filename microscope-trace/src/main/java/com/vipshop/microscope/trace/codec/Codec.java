package com.vipshop.microscope.trace.codec;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.common.cons.Constants;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * A codec for {@code LogEntry}
 *
 * @author Xu Fei
 * @version 1.0
 */
public class Codec {

    private static final TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
    private static final Base64 base64 = new Base64();

    /**
     * Encode span to {@code LogEntry}.
     *
     * @param span
     * @return
     */
    public static LogEntry toLogEntry(Span span) {
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
    public static Span toSpan(final String msg) {
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

    public static LogEntry toLogEntry(MetricData metric) {
        byte[] bytes = SerializationUtils.serialize(metric);
        String message = Base64.encodeBase64String(bytes);
        LogEntry logEntry = new LogEntry(Constants.METRIC, message);
        return logEntry;
    }

    public static MetricData toMetricData(final String msg) {
        byte[] bytes = Base64.decodeBase64(msg);
        MetricData metric = (MetricData) SerializationUtils.deserialize(bytes);
        return metric;
    }

    public static LogEntry toLogEntry(ExceptionData exception) {
        byte[] bytes = SerializationUtils.serialize(exception);
        String message = Base64.encodeBase64String(bytes);
        LogEntry logEntry = new LogEntry(Constants.EXCEPTION, message);
        return logEntry;
    }

    public static ExceptionData toExceptionData(final String msg) {
        byte[] bytes = Base64.decodeBase64(msg);
        ExceptionData exception = (ExceptionData) SerializationUtils.deserialize(bytes);
        return exception;
    }

    public static LogEntry toLogEntry(SystemData system) {
        byte[] bytes = SerializationUtils.serialize(system);
        String message = Base64.encodeBase64String(bytes);
        LogEntry logEntry = new LogEntry(Constants.SYSTEM, message);
        return logEntry;
    }

    public static SystemData toSystemData(final String msg) {
        byte[] bytes = Base64.decodeBase64(msg);
        SystemData info = (SystemData) SerializationUtils.deserialize(bytes);
        return info;
    }

}
