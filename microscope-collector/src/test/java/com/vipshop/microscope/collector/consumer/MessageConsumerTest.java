package com.vipshop.microscope.collector.consumer;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.MessageCodec;
import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.thrift.gen.Span;

public class MessageConsumerTest {
	
	MessageCodec codec = new MessageCodec();
	
	@Test
	public void testDisruptor() throws TException, InterruptedException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new CollectorServer();
				
			}
		}).start();
		for (int i = 0; i < 10; i++) {
			Span span = new Span();
			span.setAppName("picket");
			span.setAppIp("localhost");
			span.setSpanId(1);
			span.setTraceId(10000);
			span.setParentId(0);
			span.setSpanType("Action");
			span.setSpanName("name");
			span.setServerName("localhost");
			span.setResultCode("OK");
			span.setDuration(12003);
			span.setStartTime(System.currentTimeMillis());
		}
		TimeUnit.SECONDS.sleep(100);
	}
}
