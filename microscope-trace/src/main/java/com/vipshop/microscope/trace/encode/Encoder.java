package com.vipshop.microscope.trace.encode;

import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.queue.MessageCategory;

public class Encoder {

	private static final Base64 base64 = new Base64();

	private static final TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

	/**
	 * Write {@code Span} object to byte array.
	 * 
	 * @param thriftSpan
	 *            span object
	 * @return
	 * @throws TException
	 */
	private byte[] spanToBytes(final Span thriftSpan) throws TException {
		final ByteArrayOutputStream buf = new ByteArrayOutputStream();
		final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
		thriftSpan.write(proto);
		return buf.toByteArray();
	}

	public LogEntry encodeToLogEntry(Span span) throws TException {
		LogEntry logEntry = null;

		String spanAsString = base64.encodeToString(spanToBytes(span));
		logEntry = new LogEntry(MessageCategory.TRACE, spanAsString);
		return logEntry;
	}

}
