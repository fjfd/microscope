package com.vipshop.microscope.framework.span;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;

import com.vipshop.microscope.framework.thrift.LogEntry;
import com.vipshop.microscope.framework.thrift.Span;

public class Codec {

	private static final TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
	private static final Base64 base64 = new Base64();

	public Span decodeToSpan(final String msg) throws TException {
		byte[] tmp = Base64.decodeBase64(msg);
		final ByteArrayInputStream buf = new ByteArrayInputStream(tmp);
		final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
		Span span = new Span();
		span.read(proto);
		span.setResultSize(msg.length());
		return span;
	}

	public LogEntry encodeToLogEntry(Span span) throws TException {
		final ByteArrayOutputStream buf = new ByteArrayOutputStream();
		final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
		span.write(proto);
		String spanAsString = base64.encodeToString(buf.toByteArray());
		LogEntry logEntry = new LogEntry("trace", spanAsString);
		return logEntry;
	}

}
