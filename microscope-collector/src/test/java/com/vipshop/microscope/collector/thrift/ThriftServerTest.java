package com.vipshop.microscope.collector.thrift;

import java.util.Arrays;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.MessageCodec;
import com.vipshop.microscope.thrift.client.ThriftClient;
import com.vipshop.microscope.thrift.gen.LogEntry;
import com.vipshop.microscope.thrift.gen.ResultCode;
import com.vipshop.microscope.thrift.gen.Send;
import com.vipshop.microscope.thrift.gen.Span;
import com.vipshop.microscope.thrift.server.ThriftCategory;

public class ThriftServerTest {

	static class SimpleHandler implements Send.Iface {
		@Override
		public ResultCode send(List<LogEntry> messages) throws TException {
			for (LogEntry logEntry : messages) {
				Span span = new MessageCodec().decodeToSpan(logEntry.getMessage());
				Assert.assertEquals("appname", span.getAppName());
			}
			return ResultCode.OK;
		}
	}
	
	@Test
	public void testThriftServer() throws TException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				TNonblockingServerTransport serverTransport;
				try {
					serverTransport = new TNonblockingServerSocket(9410);
					Send.Processor<SimpleHandler> processor = new Send.Processor<SimpleHandler>(new SimpleHandler());
					TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
					server.serve();
				} catch (TTransportException e) {
				}
			}
		}).start();
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
		
		LogEntry logEntry = new MessageCodec().encodeToLogEntry(span);
		
		new ThriftClient("localhost", 9410, 300, ThriftCategory.NON_BLOCKING).send(Arrays.asList(logEntry));
	}
}
