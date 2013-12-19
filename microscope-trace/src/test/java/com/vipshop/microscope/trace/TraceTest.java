package com.vipshop.microscope.trace;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.span.Codec;
import com.vipshop.microscope.thrift.gen.LogEntry;
import com.vipshop.microscope.thrift.gen.ResultCode;
import com.vipshop.microscope.thrift.gen.Send;
import com.vipshop.microscope.thrift.gen.Span;

public class TraceTest {
	
	static class SimpleHandler implements Send.Iface {
		@Override
		public ResultCode send(List<LogEntry> messages) throws TException {
			for (LogEntry logEntry : messages) {
				Span span = new Codec().decodeToSpan(logEntry.getMessage());
				Assert.assertEquals("picket", span.getAppName());
			}
			return ResultCode.OK;
		}
	}
	
	@BeforeMethod
	public void testBeforeMethod() {
		Tracer.cleanContext();
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
	}
	
	@Test
	public void traceUseExample1() throws InterruptedException {
		Tracer.clientSend("example1", Category.Method);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (Exception e) {
			Tracer.setResultCode("EXCEPTION");
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(10);
	}
	
	@Test
	public void traceUseExample2() throws InterruptedException {
		Tracer.clientSend("http://www.huohu123.com", Category.URL);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
			Tracer.clientSend("getNew@newService", Category.Service);
			TimeUnit.MILLISECONDS.sleep(400);
			Tracer.clientSend("get@DB", Category.DB);
			TimeUnit.MILLISECONDS.sleep(100);
			Tracer.clientReceive();
			Tracer.clientReceive();
			
			Tracer.clientSend("buyNew@buyService", Category.Service);
			TimeUnit.MILLISECONDS.sleep(200);
			Tracer.clientSend("buy@Cache", Category.Cache);
			TimeUnit.MILLISECONDS.sleep(10);
			Tracer.clientReceive();
			Tracer.clientReceive();
		} catch (Exception e) {
			Tracer.setResultCode("EXCEPTION");
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(10);
	}
	
	@Test
	public void traceUseExample3() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			Tracer.cleanContext();
			Tracer.clientSend("example3", Category.Method);
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (Exception e) {
				Tracer.setResultCode("EXCEPTION");
			} finally {
				Tracer.clientReceive();
			}
		}
	}
	
	@Test
	public void traceUseExample4() throws InterruptedException {
		for (int i = 0; i < 10; i++)  {
			Tracer.cleanContext();
			Tracer.clientSend("http://www.huohu123.com", Category.URL);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				Tracer.clientSend("getNew@newService", Category.Service);
				TimeUnit.MILLISECONDS.sleep(400);
				Tracer.clientSend("get@DB", Category.DB);
				TimeUnit.MILLISECONDS.sleep(100);
				Tracer.clientReceive();
				Tracer.clientReceive();
				
				Tracer.clientSend("buyNew@buyService", Category.Service);
				TimeUnit.MILLISECONDS.sleep(200);
				Tracer.clientSend("buy@Cache", Category.Cache);
				TimeUnit.MILLISECONDS.sleep(10);
				Tracer.clientReceive();
				Tracer.clientReceive();
			} catch (Exception e) {
				Tracer.setResultCode("EXCEPTION");
			} finally {
				Tracer.clientReceive();
			}
		}
	}
	
}
