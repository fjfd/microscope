package com.vipshop.microscope.thrift.server;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

import com.vipshop.microscope.thrift.gen.Send;

/**
 * Thrift server.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ThriftServer {

	private Send.Iface handler;

	private int port;

	public ThriftServer(Send.Iface handler, int port) {
		this.handler = handler;
		this.port = port;
	}
	
	public void startServer(ThriftCategory category) throws TTransportException {
		
		/**
		 * Use one thread, blocking
		 */
		if (category.equals(ThriftCategory.SIMPLE)) {
			startSingleThreadServer();
		}
		
		/**
		 * None blocking
		 */
		if (category.equals(ThriftCategory.NON_BLOCKING)) {
			startNonBlockingServer();
		}
		
		/**
		 * Use thread pool
		 */
		if (category.equals(ThriftCategory.THREAD_POOL)) {
			startThreadPoolServer();
		}
		
		/**
		 *
		 */
		if (category.equals(ThriftCategory.HS_HA)) {
			startHsHaServer();
		}
		
		/**
		 * 
		 */
		if (category.equals(ThriftCategory.THREAD_SELECTOR)) {
			startThreadedSelectorServer();
		}
	}

	public void startSingleThreadServer() throws TTransportException {
		TServerTransport serverTransport = new TServerSocket(this.port);
		Send.Processor<Send.Iface> processor = new Send.Processor<Send.Iface>(this.handler);
		TServer.Args tArgs = new TServer.Args(serverTransport);
		tArgs.processor(processor);
		tArgs.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TSimpleServer(tArgs);
		server.serve();
	}

	public void startNonBlockingServer() throws TTransportException {
		TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(this.port);
		Send.Processor<Send.Iface> processor = new Send.Processor<Send.Iface>(this.handler);
		TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(serverTransport);
		tnbArgs.processor(processor);
		tnbArgs.transportFactory(new TFramedTransport.Factory());
		tnbArgs.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TNonblockingServer(tnbArgs);
		server.serve();
	}

	public void startThreadPoolServer() throws TTransportException {
		TServerTransport serverTransport = new TServerSocket(this.port);
		final Send.Processor<Send.Iface> processor = new Send.Processor<Send.Iface>(this.handler);
		TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport).processor(processor);
		args.maxWorkerThreads = 10;
		args.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TThreadPoolServer(args);
		server.serve();
	}

	public void startHsHaServer() throws TTransportException {
		TNonblockingServerSocket socket = new TNonblockingServerSocket(this.port);
		final Send.Processor<Send.Iface> processor = new Send.Processor<Send.Iface>(this.handler);
		THsHaServer.Args arg = new THsHaServer.Args(socket);
		arg.transportFactory(new TFramedTransport.Factory());
		arg.protocolFactory(new TBinaryProtocol.Factory());
		arg.processorFactory(new TProcessorFactory(processor));
		TServer server = new THsHaServer(arg);
		server.serve();
	}

	public void startThreadedSelectorServer() throws TTransportException {
		TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(this.port);
		TTransportFactory transportFactory = new TFramedTransport.Factory();
		TProtocolFactory proFactory = new TBinaryProtocol.Factory();
		final Send.Processor<Send.Iface> processor = new Send.Processor<Send.Iface>(this.handler);
		TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport);
		args.protocolFactory(proFactory);
		args.transportFactory(transportFactory);
		args.processor(processor);
		TServer server = new TThreadedSelectorServer(args);
		server.serve();
	}

}
