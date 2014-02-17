package com.vipshop.microscope.framework.thrift;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A client responsible for open connection to Thrift Server.
 * 
 * Typically the {@link ThriftClient} should be a singleton in 
 * application.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ThriftClient {
	
	private static final Logger logger = LoggerFactory.getLogger(ThriftClient.class);
	
	private final String host;
	private final int port;

	private final int reconnect;
	
	private TTransport transport;
	private TProtocol protocol;
	private Send.Client client;
	
	public ThriftClient(String host, int port, int reconnect, ThriftCategory category) {
		this.host = host;
		this.port = port;
		this.reconnect = reconnect;
		
		if (category.equals(ThriftCategory.SIMPLE)) {
			logger.info("start single thread thrift client");
			connectToSimpleServer();
		}

		if (category.equals(ThriftCategory.NON_BLOCKING)) {
			logger.info("start non blocking thrift client");
			connectToNonBlockingServer();
		}
		
		if (category.equals(ThriftCategory.HS_HA)) {
			logger.info("start hs ha thrift client");
			connectToHsHaServer();
		}
		
		if (category.equals(ThriftCategory.THREAD_POOL)) {
			logger.info("start thread pool thrift client");
			connectToThreadPoolServer();
		}
		
		if (category.equals(ThriftCategory.THREAD_SELECTOR)) {
			logger.info("start thread selector thrift client");
			connectToTheadSelectorServer();
		}
	}
	
	private void connectToSimpleServer() {
		this.transport = new TSocket(host, port, this.reconnect);
		this.protocol = new TBinaryProtocol(transport);
		this.client = new Send.Client(protocol);
		try {
			transport.open();
		} catch (Exception e) {
			this.transport.close();
		}
	}
	
	private void connectToNonBlockingServer() {
		this.transport = new TFramedTransport(new TSocket(host, port));
		this.protocol = new TBinaryProtocol(transport);
		this.client = new Send.Client(protocol);
		try {
			this.transport.open();
		} catch (TTransportException e) {
			this.transport.close();
		}
	}
	
	private void connectToThreadPoolServer() {
		this.transport = new TSocket(host, port, reconnect);
		this.protocol = new TBinaryProtocol(transport);
		this.client = new Send.Client(protocol);
		try {
			this.transport.open();
		} catch (TTransportException e) {
			this.transport.close();
		}
	}
	
	private void connectToHsHaServer() {
		this.transport = new TFramedTransport(new TSocket(host, port, reconnect));
		this.protocol = new TBinaryProtocol(transport);
		this.client = new Send.Client(protocol);
		try {
			this.transport.open();
		} catch (TTransportException e) {
			this.transport.close();
		}
	}
	
	private void connectToTheadSelectorServer() {
		this.transport = new TFramedTransport(new TSocket(host, port, reconnect));
		this.protocol = new TBinaryProtocol(transport);
		this.client = new Send.Client(protocol);
		try {
			this.transport.open();
		} catch (TTransportException e) {
			this.transport.close();
		}
	}

    /**
     * Return connect state
     * 
     * @return
     */
    public boolean isconnect() {
    	return transport.isOpen();
    }

    /**
     * Send {@code List<LogEntry>} to collector
     * 
     * @param logEntries
     */
    public void send(final List<LogEntry> logEntries) {
        try {
            client.send(logEntries);
        } catch (final TException e) {
        	transport.close();
        	resend(logEntries);
        } 
        logger.info("send " + logEntries.size() + " logEntry to collector " + host);
    }
    
    /**
     * ThriftClient reconnect to collector server.
     * 
     */
    private void resend(final List<LogEntry> logEntries) {
    	logger.info(" fail send " + logEntries.size()
    			  + " logEntry to collector, try to reconnect every " 
    			  +   reconnect + " MILLISECONDS");

    	while (!transport.isOpen()) {
    		try {
    			transport.open();
    			client.send(logEntries);
    		} catch (Exception e) {
    			
    			transport.close();
    			
    			logger.debug("ThriftClient will try to reconnect after " 
    			           + reconnect + " MILLISECONDS");
    	    	
    			try {
					TimeUnit.MILLISECONDS.sleep(reconnect);
				} catch (InterruptedException e1) {
					logger.error(e1.getMessage());
				}
    		} 
		}
 
    }
}
