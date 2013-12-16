package com.vipshop.microscope.collector.server;

import org.apache.thrift.transport.TTransportException;

import com.vipshop.micorscope.framework.util.ConfigurationUtil;
import com.vipshop.microscope.collector.consumer.MessageConsumer;
import com.vipshop.microscope.collector.consumer.MessageDisruptorConsumer;
import com.vipshop.microscope.collector.consumer.MessageQueueConsumer;
import com.vipshop.microscope.collector.receiver.MessageReceiver;
import com.vipshop.microscope.collector.receiver.ThriftMessageReceiver;
import com.vipshop.microscope.thrift.server.ThriftCategory;

/**
 * Span collector server.
 * 
 * <p>Server collecte spans from client,
 * process spans in async mode by queue.
 * 
 * <p>Store spans and analyze spans.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class CollectorServer {
	
	private static final ConfigurationUtil config = ConfigurationUtil.getConfiguration("collector.properties");
	
	private static final int COLLECTOR_PORT = config.getInt("collector_port");
	private static final int CONSUMER_POOL_SIZE = config.getInt("consumer_pool_size");
	
	public static void main(String[] args) throws TTransportException {
		MessageConsumer consumer = new MessageDisruptorConsumer(CONSUMER_POOL_SIZE);
		consumer.start();
		MessageReceiver receiver = new ThriftMessageReceiver(consumer, COLLECTOR_PORT, ThriftCategory.THREAD_SELECTOR);
		receiver.start();
	}
	
	public static void startServerBaseOnQueueAndThrift() throws TTransportException {
		MessageConsumer consumer = new MessageQueueConsumer(CONSUMER_POOL_SIZE);
		consumer.start();
		MessageReceiver receiver = new ThriftMessageReceiver(consumer, COLLECTOR_PORT, ThriftCategory.THREAD_SELECTOR);
		receiver.start();
	}
	
}
