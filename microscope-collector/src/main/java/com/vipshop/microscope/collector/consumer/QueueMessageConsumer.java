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
public class QueueMessageConsumer implements MessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueMessageConsumer.class);
	
	private final LinkedBlockingQueue<Span> storageQueue = new LinkedBlockingQueue<Span>(Integer.MAX_VALUE);
	private final LinkedBlockingQueue<Span> analyzeQueue = new LinkedBlockingQueue<Span>(Integer.MAX_VALUE);
	
	private int poolSize;
	
	private ExecutorService storageExecutor;
	private ExecutorService analyzeExecutor;
	
	public QueueMessageConsumer(int poolSize) {
		this.poolSize = poolSize;
	}
	
	@Override
	public void start() {
		logger.info("use message consumer base on LinkedBlockingQueue");
		
		logger.info("start storage thread pool with size " + poolSize);
		storageExecutor = ThreadPoolUtil.newFixedThreadPool(poolSize, "store-span-pool");
		for (int i = 0; i < poolSize; i++) {
			storageExecutor.execute(new StorageWorker(storageQueue));
		}
		
		logger.info("start analyze thread pool with size 1");
		analyzeExecutor = ThreadPoolUtil.newFixedThreadPool(1, "analyze-span-pool");
		analyzeExecutor.execute(new AnalyzeWorker(analyzeQueue));
	}

	@Override
	public void publish(Span span) {
		storageQueue.offer(span);
		analyzeQueue.offer(span);
	}

	@Override
	public void shutdown() {
		storageExecutor.shutdown();
		analyzeExecutor.shutdown();
	}

}
