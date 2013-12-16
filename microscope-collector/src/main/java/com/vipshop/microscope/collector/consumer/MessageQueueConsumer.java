package com.vipshop.microscope.collector.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.util.ThreadPoolUtil;
import com.vipshop.microscope.collector.queue.AnalyzeWorker;
import com.vipshop.microscope.collector.queue.StorageWorker;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * A version use {@link LinkedBlockingQueue} to consume spans.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageQueueConsumer implements MessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageQueueConsumer.class);
	
	private final LinkedBlockingQueue<Span> storeQueue = new LinkedBlockingQueue<Span>(Integer.MAX_VALUE);
	private final LinkedBlockingQueue<Span> analyzeQueue = new LinkedBlockingQueue<Span>(Integer.MAX_VALUE);
	
	private int poolSize;
	
	private ExecutorService storeExecutor;
	private ExecutorService analyzeExecutor;
	
	public MessageQueueConsumer(int poolSize) {
		this.poolSize = poolSize;
	}
	
	@Override
	public void publish(Span span) {
		storeQueue.offer(span);
		analyzeQueue.offer(span);
	}

	@Override
	public void start() {
		logger.info("use message consumer base on linked blocking queue ");

		logger.info("start storage thread pool with size " + poolSize);
		storeExecutor = ThreadPoolUtil.newFixedThreadPool(poolSize, "store-span-pool");
		for (int i = 0; i < poolSize; i++) {
			storeExecutor.execute(new StorageWorker(storeQueue));
		}
		
		logger.info("start analyze thread pool with size 1");
		analyzeExecutor = ThreadPoolUtil.newFixedThreadPool(1, "analyze-span-pool");
		analyzeExecutor.execute(new AnalyzeWorker(analyzeQueue));
	}

	@Override
	public void shutdown() {
		storeExecutor.shutdown();
		analyzeExecutor.shutdown();
	}

}
