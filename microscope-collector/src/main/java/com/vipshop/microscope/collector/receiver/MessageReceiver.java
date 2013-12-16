package com.vipshop.microscope.collector.receiver;

import org.apache.thrift.transport.TTransportException;

/**
 * Receive message from client.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface MessageReceiver {
	
	/**
	 * Default listen on port 9410
	 * 
	 * @throws TTransportException
	 */
	public void start() throws TTransportException;
}
