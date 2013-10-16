package com.vipshop.microscope.collector.thrift;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.vipshop.microscope.collector.server.CollectorConstant;
import com.vipshop.microscope.collector.server.CollectorHandler;
import com.vipshop.microscope.thrift.Send;

/**
 * Nonblocking thrift server.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class NonBlockingThriftServer implements ThriftServer{

	@Override
	public void serve() throws TTransportException {
		TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(CollectorConstant.COLLECTOR_PORT);
		Send.Processor<CollectorHandler> processor = new Send.Processor<CollectorHandler>(new CollectorHandler());
		TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
		server.serve();
	}

}
