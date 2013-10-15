package com.vipshop.microscope.trace.thrift;

import java.util.List;

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

	/**
	 * Flag of connection state.
	 * 
	 * If client can't connect to thrift server(zipkin collector),
	 * client tracing program should stop trace and collect. This
	 * can avoid MessageQueue become too large to handle.
	 */
	private static volatile boolean connect = true;

	private final TTransport transport;
    private final Send.Client client;

    /**
     * Create a ThriftClient.
     */
    public ThriftClient() {
        transport = new TFramedTransport(new TSocket(Constant.COLLECTOR_HOST, Constant.COLLECTOR_PORT));
        final TProtocol protocol = new TBinaryProtocol(transport);
        client = new Send.Client(protocol);
        try {
            transport.open();
        } catch (final TTransportException e) {
        	logger.info("can't open transport to collector on host: " + Constant.COLLECTOR_HOST 
        			    + ", port: " + Constant.COLLECTOR_PORT + ", program will return now");
        	connect = false;
        	return;
        }
        logger.info("open transport to collector on host: " + Constant.COLLECTOR_HOST + ", port: " + Constant.COLLECTOR_PORT);
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
        	logger.error("can't send logEntries to collector, the program will return");
        	connect = false;
        	return;
        } 
        logger.info("send " + logEntries.size() + " logEntry to zipkin");
    }

    /**
     * Close socket connection
     */
    public void close() {
        transport.close();
        connect = false;
    }
    
    /**
     * Return connect state
     * 
     * @return
     */
    public static boolean isConnect() {
    	return connect;
    }
    
}
