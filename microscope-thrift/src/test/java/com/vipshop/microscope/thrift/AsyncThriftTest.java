package com.vipshop.microscope.thrift;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransportException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.thrift.client.AsyncThriftClient;
import com.vipshop.microscope.thrift.gen.LogEntry;
import com.vipshop.microscope.thrift.gen.ResultCode;
import com.vipshop.microscope.thrift.gen.Send;
import com.vipshop.microscope.thrift.gen.Span;
import com.vipshop.microscope.thrift.server.ThriftCategory;
import com.vipshop.microscope.thrift.server.ThriftServer;

public class AsyncThriftTest {

	private static final TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
	private static final Base64 base64 = new Base64();

	@Test
	public void testNonBlockingThriftServerAsyncClient() throws TException, IOException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.NON_BLOCKING);
				} catch (TTransportException e) {
					e.printStackTrace();
				}
			}
		}).start();
		Span span = mockSpan();

		LogEntry logEntry = encodeToLogEntry(span);

		AsyncThriftClient client = new AsyncThriftClient("localhost", 9410);
		client.send(Arrays.asList(logEntry));
	}
	

	
	class SimpleHandler implements Send.Iface {
		@Override
		public ResultCode send(List<LogEntry> messages) throws TException {
			for (LogEntry logEntry : messages) {
				Span span = decodeToSpan(logEntry.getMessage());
				Assert.assertEquals("appname", span.getAppName());
			}
			return ResultCode.OK;
		}
	}

	public Span decodeToSpan(final String msg) throws TException {
		byte[] tmp = Base64.decodeBase64(msg);
		final ByteArrayInputStream buf = new ByteArrayInputStream(tmp);
		final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
		Span span = new Span();
		span.read(proto);
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

	private Span mockSpan() {
		Span span = new Span();

		span.setAppName("appname");
		span.setAppIp("localhost");
		span.setTraceId(8053381312019065847L);
		span.setParentId(8053381312019065847L);
		span.setSpanId(8053381312019065847L);
		span.setSpanName("test");
		span.setSpanType("Method");
		span.setResultCode("OK");
		span.setStartTime(System.currentTimeMillis());
		span.setDuration(1000);
		span.setResultSize(1024);
		span.setServerName("Service");
		span.setServerIp("localhost");

		return span;
	}

}
