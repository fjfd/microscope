package com.vipshop.microscope.common.thrift;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.logentry.ResultCode;
import com.vipshop.microscope.common.logentry.Send;
import com.vipshop.microscope.common.thrift.ThriftCategory;
import com.vipshop.microscope.common.thrift.ThriftClient;
import com.vipshop.microscope.common.thrift.ThriftServer;

public class ThriftTest {

	@Test
	public void testThreadSelectorThriftServer() throws TException, InterruptedException {
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
		
		TimeUnit.SECONDS.sleep(1);
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
