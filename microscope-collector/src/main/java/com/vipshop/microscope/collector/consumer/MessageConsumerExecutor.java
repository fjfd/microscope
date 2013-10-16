package com.vipshop.microscope.collector.consumer;

import java.util.concurrent.ExecutorService;

import com.vipshop.microscope.collector.server.CollectorConstant;
import com.vipshop.microscope.common.util.ThreadPoolProvider;

public class MessageConsumerExecutor {
	
	public static void startMessageConsumer() {
		int size = CollectorConstant.CONSUMER_POOL_SIZE;
		ExecutorService executor = ThreadPoolProvider.newFixedThreadPool(size, "consumer-pool");
		for (int i = 0; i < size; i++) {
			executor.execute(new MessageConsumer());
		}
	}

}
