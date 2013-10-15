package com.vipshop.microscope.collector.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.vipshop.microscope.collector.server.CollectorHandler;
import com.vipshop.microscope.common.cfg.ConfigData;
import com.vipshop.microscope.thrift.Send;

/**
 * a multiple-thread thrift server.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MultiThreadThriftServer implements ThriftServer {

	@Override
	public void serve() throws TTransportException {
		TServerTransport serverTransport = new TServerSocket(ConfigData.COLLECTOR_PORT);
		Send.Processor<CollectorHandler> processor = new Send.Processor<CollectorHandler>(new CollectorHandler());
		TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
		server.serve();		
	}

}
