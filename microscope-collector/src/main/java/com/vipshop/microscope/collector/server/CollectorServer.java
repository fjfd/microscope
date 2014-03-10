package com.vipshop.microscope.collector.server;

import org.apache.thrift.transport.TTransportException;

import com.vipshop.microscope.collector.consumer.MessageConsumer;
import com.vipshop.microscope.collector.consumer.DisruptorMessageConsumer;
import com.vipshop.microscope.collector.receiver.MessageReceiver;
import com.vipshop.microscope.collector.receiver.ThriftMessageReceiver;
import com.vipshop.microscope.common.thrift.ThriftCategory;
import com.vipshop.microscope.common.util.ConfigurationUtil;

/**
 * Message collector server.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class CollectorServer {
	
	private static final ConfigurationUtil config = ConfigurationUtil.getConfiguration("collector.properties");
	
	public static final int COLLECTOR_PORT = config.getInt("collector_port");
	public static final int CONSUMER_POOL_SIZE = config.getInt("consumer_pool_size");
	public static final int SLEEP_TIME = config.getInt("sleep_time");
	
	/**
	 * Consume message to {@code RingBuffer}.
	 */
	private MessageConsumer consumer;
	
	/**
	 * Receive message from client.
	 */
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
	
	/**
	 * Main collector entrance.
	 * 
	 * @param args
	 * @throws TTransportException
	 */
	public static void main(String[] args) throws TTransportException {
		CollectorServer server = new CollectorServer();
		server.start();
	}
	
}
