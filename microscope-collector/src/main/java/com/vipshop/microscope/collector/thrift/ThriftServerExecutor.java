package com.vipshop.microscope.collector.thrift;

import org.apache.thrift.transport.TTransportException;

public class ThriftServerExecutor {
	
	public static void startSingleThreadServer() throws TTransportException {
		ThriftServer thriftServer = new SingleThreadThriftServer();
		thriftServer.serve();
	}
	
	public static void startMultiThreadServer() throws TTransportException {
		ThriftServer thriftServer = new MultiThreadThriftServer();
		thriftServer.serve();
	}
	
	public static void startNonBlockingServer() throws TTransportException {
		ThriftServer thriftServer = new NonBlockingThriftServer();
		thriftServer.serve();
	}
}
