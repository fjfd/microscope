package com.vipshop.microscope.collector.receiver;

import com.vipshop.microscope.collector.consumer.MessageConsumer;
import com.vipshop.microscope.trace.gen.LogEntry;
import com.vipshop.microscope.trace.gen.ResultCode;
import com.vipshop.microscope.trace.gen.Send;
import com.vipshop.microscope.trace.thrift.ThriftCategory;
import com.vipshop.microscope.trace.thrift.ThriftServer;
import org.apache.thrift.TException;

import java.util.List;

/**
 * Use {@code ThriftServer} receive {@code LogEntry}.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ThriftMessageReceiver implements MessageReceiver {

    private ThriftServer thriftServer;

    private ThriftCategory category;

    /**
     * Construct {@code ThriftMessageReceiver}.
     *
     * @param consumer
     * @param port
     * @param category
     */
    public ThriftMessageReceiver(MessageConsumer consumer, int port, ThriftCategory category) {
        this.thriftServer = new ThriftServer(new ThriftReceiveHandler(consumer), port);
        this.category = category;
    }

    /**
     * Start thrift server.
     */
    public void start() {
        thriftServer.startServer(category);
    }

    /**
     * Thrift handler.
     * <p/>
     * publish message to consumer.
     *
     * @author Xu Fei
     * @version 1.0
     */
    static class ThriftReceiveHandler implements Send.Iface {

        final MessageConsumer consumer;

        public ThriftReceiveHandler(MessageConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public ResultCode send(List<LogEntry> messages) throws TException {
            for (LogEntry logEntry : messages) {
                consumer.publish(logEntry);
            }
            return ResultCode.OK;
        }
    }

}
