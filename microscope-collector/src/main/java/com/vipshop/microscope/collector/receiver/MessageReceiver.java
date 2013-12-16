package com.vipshop.microscope.collector.receiver;

import org.apache.thrift.transport.TTransportException;

public interface MessageReceiver {
	
	public void start() throws TTransportException;
}
