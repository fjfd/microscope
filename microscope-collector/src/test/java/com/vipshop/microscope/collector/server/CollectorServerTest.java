package com.vipshop.microscope.collector.server;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.Codec;
import com.vipshop.microscope.thrift.client.ThriftClient;
import com.vipshop.microscope.thrift.gen.LogEntry;
import com.vipshop.microscope.thrift.gen.Span;
import com.vipshop.microscope.thrift.server.ThriftCategory;

public class CollectorServerTest {
	
	@Test
	public void testCollectorServer() throws TException, InterruptedException {
		
		new Thread(new CollectorServer()).start();
		
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
		
		LogEntry logEntry = new Codec().encodeToLogEntry(span);
		
		new ThriftClient("localhost", 9410, 3000, ThriftCategory.NON_BLOCKING).send(Arrays.asList(logEntry, logEntry, logEntry));
		
		TimeUnit.SECONDS.sleep(5);

	}
}
