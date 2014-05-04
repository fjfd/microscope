package com.vipshop.microscope.thrift;

import org.apache.commons.codec.binary.Base64;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * LogEntry code/decode util
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ThriftCodec {

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
        LogEntry logEntry = new LogEntry("trace", spanAsString);
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
}
