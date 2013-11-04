package com.vipshop.microscope.collector.server;

import org.apache.thrift.transport.TTransportException;
import org.testng.annotations.Test;

public class CollectorServerTest {
	
	@Test
	public void testStart() throws TTransportException {
		CollectorServer.start();
	}
}
