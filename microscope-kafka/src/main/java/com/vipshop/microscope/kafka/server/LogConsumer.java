package com.vipshop.microscope.kafka.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class LogConsumer {

	private ConsumerConfig config;
	private String topic;
	private int partitionsNum;
	private MessageExecutor executor;
	private ConsumerConnector connector;
	private ExecutorService threadPool;

	public LogConsumer(String topic, int partitionsNum, MessageExecutor executor) throws Exception {
		Properties properties = new Properties();
		properties.load(ClassLoader.getSystemResourceAsStream("consumer.properties"));
		config = new ConsumerConfig(properties);
		this.topic = topic;
		this.partitionsNum = partitionsNum;
		this.executor = executor;
	}

	public void start() throws Exception {
		connector = Consumer.createJavaConsumerConnector(config);
		Map<String, Integer> topics = new HashMap<String, Integer>();
		topics.put(topic, partitionsNum);
		Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(topics);
		List<KafkaStream<byte[], byte[]>> partitions = streams.get(topic);
		threadPool = Executors.newFixedThreadPool(partitionsNum);
		for (KafkaStream<byte[], byte[]> partition : partitions) {
			threadPool.execute(new MessageRunner(partition));
		}
	}

	public void close() {
		try {
			threadPool.shutdownNow();
		} catch (Exception e) {
			//
		} finally {
			connector.shutdown();
		}

	}

	class MessageRunner implements Runnable {
		private KafkaStream<byte[], byte[]> partition;

		MessageRunner(KafkaStream<byte[], byte[]> partition) {
			this.partition = partition;
		}

		public void run() {
			ConsumerIterator<byte[], byte[]> it = partition.iterator();
			while (it.hasNext()) {
				// connector.commitOffsets();手动提交offset,当autocommit.enable=false时使用
				MessageAndMetadata<byte[], byte[]> item = it.next();
				System.out.println("partiton:" + item.partition());
				System.out.println("offset:" + item.offset());
				executor.execute(new String(item.message()));// UTF-8,注意异常
			}
		}
	}

	interface MessageExecutor {

		public void execute(String message);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogConsumer consumer = null;
		try {
			MessageExecutor executor = new MessageExecutor() {

				public void execute(String message) {
					System.out.println(message);

				}
			};
			consumer = new LogConsumer("test-topic", 2, executor);
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// if(consumer != null){
			// consumer.close();
			// }
		}

	}

}