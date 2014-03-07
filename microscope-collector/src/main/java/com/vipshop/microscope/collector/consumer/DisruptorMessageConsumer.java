package com.vipshop.microscope.collector.consumer;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.microscope.collector.disruptor.ExceptionEvent;
import com.vipshop.microscope.collector.disruptor.ExceptionStorageHandler;
import com.vipshop.microscope.collector.disruptor.TraceAnalyzeHandler;
import com.vipshop.microscope.collector.disruptor.TraceStorageHandler;
import com.vipshop.microscope.collector.disruptor.TraceEvent;
import com.vipshop.microscope.common.span.Codec;
import com.vipshop.microscope.common.thrift.LogEntry;
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

	private final int TRACE_BUFFER_SIZE = 1024 * 8 * 8 * 4;
//	private final int STATS_BUFFER_SIZE = 1024 * 8 * 8 * 3;
	private final int EXCEP_BUFFER_SIZE = 1024 * 8 * 8 * 1;
	
	private volatile boolean start = false;
	
	private final RingBuffer<TraceEvent> traceRingBuffer;
	private final SequenceBarrier traceSequenceBarrier;
//	private final BatchEventProcessor<TraceEvent> traceAlertEventProcessor;
	private final BatchEventProcessor<TraceEvent> traceAnalyzeEventProcessor;
	private final BatchEventProcessor<TraceEvent> traceStorageEventProcessor;
	
	private final RingBuffer<ExceptionEvent> excepRingBuffer;
	private final SequenceBarrier excepSequenceBarrier;
	private final BatchEventProcessor<ExceptionEvent> excepStorageEventProcessor;
	

	public DisruptorMessageConsumer() {
		this.traceRingBuffer = RingBuffer.createSingleProducer(TraceEvent.EVENT_FACTORY, TRACE_BUFFER_SIZE, new SleepingWaitStrategy());
		this.traceSequenceBarrier = traceRingBuffer.newBarrier();

//		this.traceAlertEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceAlertHandler());
		this.traceAnalyzeEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceAnalyzeHandler());
		this.traceStorageEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceStorageHandler());
		
//		this.traceRingBuffer.addGatingSequences(traceAlertEventProcessor.getSequence());
		this.traceRingBuffer.addGatingSequences(traceAnalyzeEventProcessor.getSequence());
		this.traceRingBuffer.addGatingSequences(traceStorageEventProcessor.getSequence());
		
		this.excepRingBuffer = RingBuffer.createSingleProducer(ExceptionEvent.EVENT_FACTORY, EXCEP_BUFFER_SIZE, new SleepingWaitStrategy());
		this.excepSequenceBarrier = excepRingBuffer.newBarrier();
		
		this.excepStorageEventProcessor = new BatchEventProcessor<ExceptionEvent>(excepRingBuffer, excepSequenceBarrier, new ExceptionStorageHandler());
		
		this.excepRingBuffer.addGatingSequences(excepStorageEventProcessor.getSequence());
	}
	
	public void start() {
		logger.info("use message consumer base on disruptor");
		
//		logger.info("start alert thread pool with size 1");
//		ExecutorService alertExecutor = ThreadPoolUtil.newFixedThreadPool(1, "alert-span-pool");
//		alertExecutor.execute(this.traceAlertEventProcessor);

		logger.info("start trace message analyze thread pool with size 1");
		ExecutorService traceAnalyzeExecutor = ThreadPoolUtil.newFixedThreadPool(1, "analyze-trace-pool");
		traceAnalyzeExecutor.execute(this.traceAnalyzeEventProcessor);
		
		logger.info("start trace message storage thread pool with size 1");
		ExecutorService traceStorageExecutor = ThreadPoolUtil.newFixedThreadPool(1, "store-trace-pool");
		traceStorageExecutor.execute(this.traceStorageEventProcessor);
		
		logger.info("start excep message storage thread pool with size 1");
		ExecutorService excepStorageExecutor = ThreadPoolUtil.newFixedThreadPool(1, "store-excep-pool");
		excepStorageExecutor.execute(this.excepStorageEventProcessor);
		
		start = true;
	}
	
	public void publish(LogEntry logEntry) {
		String category = logEntry.getCategory();
		
		// handle trace message
		if (category.equals("trace")) {
			publishTrace(logEntry);
		} 
		
		// handle stats message
		if (category.equals("stats")) {
			publishStats(logEntry);
		}
		
		// handle exception message
		if (category.equals("exception")) {
			publishException(logEntry);
		}
	}
	
	private void publishTrace(LogEntry logEntry) {
		Span span = null;
		try {
			span = Codec.decodeToSpan(logEntry.getMessage());
		} catch (TException e) {
			logger.warn("decode to span error", e);
		} 
		if (start && span != null) {
			long sequence = this.traceRingBuffer.next();
			this.traceRingBuffer.get(sequence).setSpan(span);
			this.traceRingBuffer.publish(sequence);
		}
	}
	
	private void publishStats(LogEntry logEntry) {
		
	}
	
	private void publishException(LogEntry logEntry) {
		String message = logEntry.getMessage();
		Map<String, Object> map = Codec.decodeToMap(message);
		if (start && map != null) {
			long sequence = this.excepRingBuffer.next();
			this.excepRingBuffer.get(sequence).setMap(map);
			this.excepRingBuffer.publish(sequence);
		}
	}
	
	public void shutdown() {
//		traceAlertEventProcessor.halt();
		traceAnalyzeEventProcessor.halt();
		traceStorageEventProcessor.halt();
		
		excepStorageEventProcessor.halt();
	}

}
