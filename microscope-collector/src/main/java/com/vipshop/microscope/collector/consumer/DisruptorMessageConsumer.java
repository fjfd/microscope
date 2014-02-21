package com.vipshop.microscope.collector.consumer;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.microscope.collector.disruptor.MessageAlertHandler;
import com.vipshop.microscope.collector.disruptor.MessageAnalyzeHandler;
import com.vipshop.microscope.collector.disruptor.MessageStorageHandler;
import com.vipshop.microscope.collector.disruptor.SpanEvent;
import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.common.util.ThreadPoolUtil;

/**
 * A version use {@code Disruptor} to consume spans.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class DisruptorMessageConsumer implements MessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(DisruptorMessageConsumer.class);

	private final int BUFFER_SIZE = 1024 * 8 * 8 * 8;
	
	private volatile boolean start = false;
	
	private final RingBuffer<SpanEvent> ringBuffer;
	
	private final SequenceBarrier sequenceBarrier;
	
	private final BatchEventProcessor<SpanEvent> alertEventProcessor;
	private final BatchEventProcessor<SpanEvent> analyzeEventProcessor;
	private final BatchEventProcessor<SpanEvent> storageEventProcessor;
	
	public DisruptorMessageConsumer() {
		this.ringBuffer = RingBuffer.createSingleProducer(SpanEvent.EVENT_FACTORY, BUFFER_SIZE, new SleepingWaitStrategy());
		
		this.sequenceBarrier = ringBuffer.newBarrier();
		
		this.alertEventProcessor = new BatchEventProcessor<SpanEvent>(ringBuffer, sequenceBarrier, new MessageAlertHandler());
		this.analyzeEventProcessor = new BatchEventProcessor<SpanEvent>(ringBuffer, sequenceBarrier, new MessageAnalyzeHandler());
		this.storageEventProcessor = new BatchEventProcessor<SpanEvent>(ringBuffer, sequenceBarrier, new MessageStorageHandler());
		
		this.ringBuffer.addGatingSequences(alertEventProcessor.getSequence());
		this.ringBuffer.addGatingSequences(analyzeEventProcessor.getSequence());
		this.ringBuffer.addGatingSequences(storageEventProcessor.getSequence());
	}
	
	public void start() {
		logger.info("use message consumer base on disruptor ");
		
		logger.info("start alert thread pool with size 1");
		ExecutorService alertExecutor = ThreadPoolUtil.newFixedThreadPool(1, "alert-span-pool");
		alertExecutor.execute(this.alertEventProcessor);

		logger.info("start analyze thread pool with size 1");
		ExecutorService analyzeExecutor = ThreadPoolUtil.newFixedThreadPool(1, "analyze-span-pool");
		analyzeExecutor.execute(this.analyzeEventProcessor);
		
		logger.info("start storage thread pool with size 1");
		ExecutorService storageExecutor = ThreadPoolUtil.newFixedThreadPool(1, "store-span-pool");
		storageExecutor.execute(this.storageEventProcessor);
		
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
		alertEventProcessor.halt();
		analyzeEventProcessor.halt();
		storageEventProcessor.halt();
	}

}
