package com.vipshop.microscope.thrift;

import java.util.Arrays;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.thrift.client.ThriftClient;
import com.vipshop.microscope.thrift.gen.LogEntry;
import com.vipshop.microscope.thrift.gen.ResultCode;
import com.vipshop.microscope.thrift.gen.Send;
import com.vipshop.microscope.thrift.server.ThriftCategory;
import com.vipshop.microscope.thrift.server.ThriftServer;

public class ThriftTest {

	@Test
	public void testSimpleThriftServer() throws TException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.SIMPLE);
				} catch (TTransportException e) {
					e.printStackTrace();
				}
			}
		}).start();

		LogEntry logEntry = new LogEntry("test", "message");
		ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.SIMPLE);
		client.send(Arrays.asList(logEntry));
	}


	@Test
	public void testNonBlockingThriftServer() throws TException {
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
		LogEntry logEntry = new LogEntry("test", "message");

		ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.NON_BLOCKING);
		client.send(Arrays.asList(logEntry));
	}
	
	@Test
	public void testThreadPoolThriftServer() throws TException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.THREAD_POOL);
				} catch (TTransportException e) {
					e.printStackTrace();
				}
			}
		}).start();
		LogEntry logEntry = new LogEntry("test", "message");
		ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.THREAD_POOL);
		client.send(Arrays.asList(logEntry));
	}
	
	@Test
	public void testHsHaThriftServer() throws TException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.HS_HA);
				} catch (TTransportException e) {
					e.printStackTrace();
				}
			}
		}).start();
		LogEntry logEntry = new LogEntry("test", "message");

		ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.HS_HA);
		client.send(Arrays.asList(logEntry));
	}
	
	@Test
	public void testThreadSelectorThriftServer() throws TException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.THREAD_SELECTOR);
				} catch (TTransportException e) {
					e.printStackTrace();
				}
			}
		}).start();
		LogEntry logEntry = new LogEntry("test", "message");

		ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.THREAD_SELECTOR);
		client.send(Arrays.asList(logEntry));
	}
	
	class SimpleHandler implements Send.Iface {
		@Override
		public ResultCode send(List<LogEntry> messages) throws TException {
			for (LogEntry logEntry : messages) {
				Assert.assertEquals("message", logEntry.getMessage());
			}
			return ResultCode.OK;
		}
	}

}
