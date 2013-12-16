package com.vipshop.microscope.collector.consumer;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;
import com.vipshop.micorscope.framework.util.ThreadPoolUtil;
import com.vipshop.microscope.collector.disruptor.AnalyzeEventHandler;
import com.vipshop.microscope.collector.disruptor.SpanEvent;
import com.vipshop.microscope.collector.disruptor.StoreWorkHandler;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * A version use {@code Disruptor} to consume spans.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageDisruptorConsumer implements MessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageDisruptorConsumer.class);
	
	private static final int BUFFER_SIZE = 1024 * 8 * 8 * 8;
	
	private volatile boolean start = false;
	
	private final RingBuffer<SpanEvent> ringBuffer;
	private final SequenceBarrier sequenceBarrier;
	private final WorkHandler<SpanEvent>[] storeHandler;
	private final AnalyzeEventHandler analyzeHandler;
	private final WorkerPool<SpanEvent> storeWorkerPool;
	private final BatchEventProcessor<SpanEvent> analyzeEventProcessor;
	private final int poolSize;
	
	public MessageDisruptorConsumer(int poolSize) {
		this.poolSize = poolSize;
		ringBuffer = RingBuffer.createSingleProducer(SpanEvent.EVENT_FACTORY, BUFFER_SIZE, new SleepingWaitStrategy());
		sequenceBarrier = ringBuffer.newBarrier();
		storeHandler = new StoreWorkHandler[poolSize];
		for (int i = 0; i < poolSize; i++) {
			storeHandler[i] = new StoreWorkHandler();
		}
		analyzeHandler = new AnalyzeEventHandler();
		storeWorkerPool = new WorkerPool<SpanEvent>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), storeHandler);
		analyzeEventProcessor = new BatchEventProcessor<SpanEvent>(ringBuffer, sequenceBarrier, analyzeHandler);
		ringBuffer.addGatingSequences(storeWorkerPool.getWorkerSequences());
		ringBuffer.addGatingSequences(analyzeEventProcessor.getSequence());
	}
	
	public void start() {
		logger.info("use message consumer base on disruptor ");

		logger.info("start storage thread pool with size " + poolSize);
		ExecutorService executor = ThreadPoolUtil.newFixedThreadPool(poolSize, "store-span-pool");
		storeWorkerPool.start(executor);
		
		logger.info("start analyze thread pool with size 1");
		ExecutorService analyzeExecutor = ThreadPoolUtil.newFixedThreadPool(1, "analyze-span-pool");
		analyzeExecutor.execute(this.analyzeEventProcessor);
		start = true;
	}
	
	public void publish(Span span) {
		if (start && span != null) {
			long sequence = this.ringBuffer.next();
			this.ringBuffer.get(sequence).setSpan(span);
			this.ringBuffer.publish(sequence);
		}
	}
	
	public void shutdown() {
		storeWorkerPool.halt();
		analyzeEventProcessor.halt();
	}

}
