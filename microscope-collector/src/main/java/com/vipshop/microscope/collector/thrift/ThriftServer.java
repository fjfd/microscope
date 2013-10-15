package com.vipshop.microscope.collector.thrift;

import org.apache.thrift.transport.TTransportException;

public interface ThriftServer {
	
	public void serve() throws TTransportException;
}
