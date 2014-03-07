package com.vipshop.microscope.common.logentry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;

import com.vipshop.microscope.common.trace.Span;

public class Codec {
	
	private static final TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
	private static final Base64 base64 = new Base64();

	public static LogEntry encodeToLogEntry(Span span) throws TException {
		final ByteArrayOutputStream buf = new ByteArrayOutputStream();
		final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
		span.write(proto);
		String spanAsString = base64.encodeToString(buf.toByteArray());
		LogEntry logEntry = new LogEntry(LogEntryCategory.TRACE, spanAsString);
		return logEntry;
	}

	public static Span decodeToSpan(final String msg) throws TException {
		byte[] tmp = Base64.decodeBase64(msg);
		final ByteArrayInputStream buf = new ByteArrayInputStream(tmp);
		final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
		Span span = new Span();
		span.read(proto);
		span.setResultSize(msg.length());
		return span;
	}

	public static LogEntry encodeToLogEntry(Map<String, Object> map) {
		byte[] bytes = SerializationUtils.serialize((Serializable) map);
		String message = Base64.encodeBase64String(bytes);
		
		LogEntry logEntry = new LogEntry(LogEntryCategory.EXCEP, message);
		return logEntry;
	}
	
	public static Map<String, Object> decodeToMap(final String msg) { 
		byte[] bytes = Base64.decodeBase64(msg);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (HashMap<String, Object>) SerializationUtils.deserialize(bytes);
		return map;
	}
	
}
