package com.vipshop.microscope.trace.thrift;

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

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Send;
import com.vipshop.microscope.trace.Constant;

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

	private final TTransport transport = new TFramedTransport(new TSocket(Constant.COLLECTOR_HOST, Constant.COLLECTOR_PORT));
	private final TProtocol protocol = new TBinaryProtocol(transport);
    private final Send.Client client = new Send.Client(protocol);
    
    public ThriftClient() {
    	try {
			transport.open();
		} catch (TTransportException e) {
			transport.close();
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
        logger.info("send " + logEntries.size() + " logEntry to collector");
    }
    
    /**
     * ThriftClient reconnect to collector server.
     * 
     */
    private void resend(final List<LogEntry> logEntries) {
    	
    	logger.info("fail send " + logEntries.size() + " logEntry to collector, try to reconnect");
    	
    	while (!transport.isOpen()) {
    		try {
    			transport.open();
    			client.send(logEntries);
    		} catch (Exception e) {
    			
    			transport.close();
    			
    			logger.info("ThriftClient will try to reconnect after " + Constant.RECONNECT_WAIT_TIME + " MILLISECONDS");
    	    	
    			try {
					TimeUnit.MILLISECONDS.sleep(Constant.RECONNECT_WAIT_TIME);
				} catch (InterruptedException e1) {
					logger.error(e1.getMessage());
				}
    		} 
		}
 
    }
}
