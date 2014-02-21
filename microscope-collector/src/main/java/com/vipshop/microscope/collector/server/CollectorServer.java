package com.vipshop.microscope.collector.server;

import org.apache.thrift.transport.TTransportException;

import com.vipshop.microscope.collector.consumer.MessageConsumer;
import com.vipshop.microscope.collector.consumer.DisruptorMessageConsumer;
import com.vipshop.microscope.collector.receiver.MessageReceiver;
import com.vipshop.microscope.collector.receiver.ThriftMessageReceiver;
import com.vipshop.microscope.common.thrift.ThriftCategory;
import com.vipshop.microscope.common.util.ConfigurationUtil;

/**
 * Span collector server.
 * 
 * <p>Server collect spans from client,
 * process spans in async mode by queue.
 * 
 * <p>Store spans and analyze spans.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class CollectorServer implements Runnable {
	
	private static final ConfigurationUtil config = ConfigurationUtil.getConfiguration("collector.properties");
	
	public static final int COLLECTOR_PORT = config.getInt("collector_port");
	public static final int CONSUMER_POOL_SIZE = config.getInt("consumer_pool_size");
	public static final int SLEEP_TIME = config.getInt("sleep_time");
	
	private MessageConsumer consumer;
	
	private MessageReceiver receiver;
	
	/**
	 * Default collector server.
	 */
	public CollectorServer() {
		this.consumer = new DisruptorMessageConsumer();
		this.receiver = new ThriftMessageReceiver(consumer, COLLECTOR_PORT, ThriftCategory.THREAD_SELECTOR);
	}
	
	/**
	 * Create collector server.
	 * 
	 * @param consumer
	 * @param receiver
	 */
	public CollectorServer(MessageConsumer consumer, MessageReceiver receiver) {
		this.consumer = consumer;
		this.receiver = receiver;
	}
	
	/**
	 * Start collector server.
	 * 
	 * @throws TTransportException
	 */
	public void start() throws TTransportException {
		consumer.start();
		receiver.start();
	}
	
	@Override
	public void run() {
		CollectorServer server = new CollectorServer();
		try {
			server.start();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws TTransportException {
		CollectorServer server = new CollectorServer();
		server.start();
	}

	
}
