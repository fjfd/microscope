package com.vipshop.microscope.trace.thrift;

import com.vipshop.microscope.trace.gen.LogEntry;
import com.vipshop.microscope.trace.gen.Send;
import com.vipshop.microscope.trace.gen.Send.AsyncClient.send_call;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Async thrift client.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ThriftAsyncClient {

    private static final Logger logger = LoggerFactory.getLogger(ThriftAsyncClient.class);

    private final String host;
    private final int port;
    private final SendCallback callback = new SendCallback();
    private Send.AsyncClient client;

    public ThriftAsyncClient(String host, int port) throws IOException, TTransportException {
        this.host = host;
        this.port = port;
        this.client = new Send.AsyncClient(new TBinaryProtocol.Factory(),
                new TAsyncClientManager(),
                new TNonblockingSocket(host, port));

        logger.info("set up client on host " + this.host + " , port " + this.port);
    }

    /**
     * Send {@code List<LogEntry>} to collector
     *
     * @param logEntries
     */
    public void send(final List<LogEntry> logEntries) {
        try {
            client.send(logEntries, callback);
        } catch (final TException e) {

        }
        logger.info("send " + logEntries.size() + " logEntry to collector " + host);
    }

    /**
     * A callback for send.
     *
     * @author Xu Fei
     * @version 1.0
     */
    static class SendCallback implements AsyncMethodCallback<send_call> {

        @Override
        public void onComplete(send_call response) {
            // TODO
        }

        @Override
        public void onError(Exception exception) {
            // TODO
        }

    }

}
