package com.vipshop.microscope.collector;

import com.vipshop.microscope.collector.consumer.DisruptorMessageConsumer;
import com.vipshop.microscope.collector.consumer.MessageConsumer;
import com.vipshop.microscope.collector.receiver.KafkaMessageReceiver;
import com.vipshop.microscope.collector.receiver.MessageReceiver;
import com.vipshop.microscope.collector.receiver.ThriftMessageReceiver;
import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.thrift.ThriftCategory;

/**
 * Message collector server.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class CollectorServer {

    private static final ConfigurationUtil config = ConfigurationUtil.getConfiguration("collector.properties");

    public static final int COLLECTOR_PORT = config.getInt("collector_port");
    public static final int SLEEP_TIME = config.getInt("sleep_time");

    /**
     * Consume message to {@code RingBuffer}.
     */
    private MessageConsumer consumer;

    /**
     * Receive message from thrift client.
     */
    private MessageReceiver thriftReceiver;

    /**
     * Read message from kafka consumer
     */
    private MessageReceiver kafkaReceiver;

    /**
     * Default collector server.
     */
    public CollectorServer() {
        this.consumer = new DisruptorMessageConsumer();
        this.kafkaReceiver = new KafkaMessageReceiver(consumer);
        this.thriftReceiver = new ThriftMessageReceiver(consumer, COLLECTOR_PORT, ThriftCategory.THREAD_SELECTOR);
    }

    /**
     * Create collector server.
     *
     * @param consumer
     * @param thriftReceiver
     */
    public CollectorServer(MessageConsumer consumer, MessageReceiver thriftReceiver, MessageReceiver kafkaReceiver) {
        this.consumer = consumer;
        this.thriftReceiver = thriftReceiver;
        this.kafkaReceiver = kafkaReceiver;
    }

    /**
     * Main collector entrance.
     *
     * @param args
     */
    public static void main(String[] args) {
//        AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-collector.xml", CollectorServer.class);
//        context.close();

        CollectorServer server = new CollectorServer();
        server.start();

    }

    /**
     * Start collector server.
     */
    public void start() {
        this.consumer.start();
        this.thriftReceiver.start();
        this.kafkaReceiver.start();
    }

}
