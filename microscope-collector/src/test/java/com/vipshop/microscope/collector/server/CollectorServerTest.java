package com.vipshop.microscope.collector.server;

import org.apache.thrift.transport.TTransportException;
import org.testng.annotations.Test;

import com.vipshop.microscope.collector.CollectorServer;

public class CollectorServerTest {
	
	@Test
	public void startServer() throws TTransportException {
		CollectorServer server = new CollectorServer();
		server.start();
	}
}
