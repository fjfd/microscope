package com.vipshop.microscope.collector.consumer;

import java.util.concurrent.ExecutorService;

import com.vipshop.microscope.common.cfg.ConfigData;
import com.vipshop.microscope.common.util.ThreadPoolProvider;

public class MessageConsumerExecutor {
	
	public static void startMessageConsumer() {
		int size = ConfigData.CONSUMER_POOL_SIZE;
		ExecutorService executor = ThreadPoolProvider.newFixedThreadPool(size, "consumer-pool");
		for (int i = 0; i < size; i++) {
			executor.execute(new MessageConsumer());
		}
	}

}
