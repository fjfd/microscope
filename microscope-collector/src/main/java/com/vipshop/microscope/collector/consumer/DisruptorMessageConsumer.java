package com.vipshop.microscope.collector.consumer;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.microscope.collector.disruptor.MetricsAlertHandler;
import com.vipshop.microscope.collector.disruptor.MetricsAnalyzeHandler;
import com.vipshop.microscope.collector.disruptor.MetricsEvent;
import com.vipshop.microscope.collector.disruptor.MetricsStorageHandler;
import com.vipshop.microscope.collector.disruptor.TraceAlertHandler;
import com.vipshop.microscope.collector.disruptor.TraceAnalyzeHandler;
import com.vipshop.microscope.collector.disruptor.TraceEvent;
import com.vipshop.microscope.collector.disruptor.TraceStorageHandler;
import com.vipshop.microscope.collector.validater.MessageValidater;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.logentry.LogEntryCategory;
import com.vipshop.microscope.common.logentry.LogEntryCodec;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.util.ThreadPoolUtil;

/**
 * A version use {@code Disruptor} to consume message.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class DisruptorMessageConsumer implements MessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(DisruptorMessageConsumer.class);

	private final int TRACE_BUFFER_SIZE   = 1024 * 8 * 8 * 4;
	private final int METRICS_BUFFER_SIZE = 1024 * 8 * 8 * 4;
	
	private volatile boolean start = false;
	
	/**
	 * Trace RingBuffer
	 */
	private final RingBuffer<TraceEvent> traceRingBuffer;
	private final SequenceBarrier traceSequenceBarrier;
	private final BatchEventProcessor<TraceEvent> traceAlertEventProcessor;
	private final BatchEventProcessor<TraceEvent> traceAnalyzeEventProcessor;
	private final BatchEventProcessor<TraceEvent> traceStorageEventProcessor;
	
	/**
	 * Metrics RingBuffer
	 */
	private final RingBuffer<MetricsEvent> metricsRingBuffer;
	private final SequenceBarrier metricsSequenceBarrier;
	private final BatchEventProcessor<MetricsEvent> metricsAlertEventProcessor;
	private final BatchEventProcessor<MetricsEvent> metricsAnalyzeEventProcessor;
	private final BatchEventProcessor<MetricsEvent> metricsStorageEventProcessor;
	
	/**
	 * Construct DisruptorMessageConsumer
	 */
	public DisruptorMessageConsumer() {
		this.traceRingBuffer = RingBuffer.createSingleProducer(TraceEvent.EVENT_FACTORY, TRACE_BUFFER_SIZE, new SleepingWaitStrategy());
		this.traceSequenceBarrier = traceRingBuffer.newBarrier();
		this.traceAlertEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceAlertHandler());
		this.traceAnalyzeEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceAnalyzeHandler());
		this.traceStorageEventProcessor = new BatchEventProcessor<TraceEvent>(traceRingBuffer, traceSequenceBarrier, new TraceStorageHandler());
		this.traceRingBuffer.addGatingSequences(traceAlertEventProcessor.getSequence());
		this.traceRingBuffer.addGatingSequences(traceAnalyzeEventProcessor.getSequence());
		this.traceRingBuffer.addGatingSequences(traceStorageEventProcessor.getSequence());
		
		this.metricsRingBuffer = RingBuffer.createSingleProducer(MetricsEvent.EVENT_FACTORY, METRICS_BUFFER_SIZE, new SleepingWaitStrategy());
		this.metricsSequenceBarrier = metricsRingBuffer.newBarrier();
		this.metricsAlertEventProcessor = new BatchEventProcessor<MetricsEvent>(metricsRingBuffer, metricsSequenceBarrier, new MetricsAlertHandler());
		this.metricsAnalyzeEventProcessor = new BatchEventProcessor<MetricsEvent>(metricsRingBuffer, metricsSequenceBarrier, new MetricsAnalyzeHandler());
		this.metricsStorageEventProcessor = new BatchEventProcessor<MetricsEvent>(metricsRingBuffer, metricsSequenceBarrier, new MetricsStorageHandler());
		this.metricsRingBuffer.addGatingSequences(metricsAlertEventProcessor.getSequence());
		this.metricsRingBuffer.addGatingSequences(metricsAnalyzeEventProcessor.getSequence());
		this.metricsRingBuffer.addGatingSequences(metricsStorageEventProcessor.getSequence());
		
	}
	
	/**
	 * Start event processors
	 */
	@Override
	public void start() {
		logger.info("use message consumer base on disruptor");
		
		logger.info("start trace alert thread");
		ExecutorService traceAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-alert-pool");
		traceAlertExecutor.execute(this.traceAlertEventProcessor);

		logger.info("start trace analyze thread");
		ExecutorService traceAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-analyze-pool");
		traceAnalyzeExecutor.execute(this.traceAnalyzeEventProcessor);
		
		logger.info("start trace store thread");
		ExecutorService traceStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-store-pool");
		traceStoreExecutor.execute(this.traceStorageEventProcessor);
		
		logger.info("start metrics alert thread");
		ExecutorService metricsAlertExecutor = ThreadPoolUtil.newSingleThreadExecutor("metrics-alert-pool");
		metricsAlertExecutor.execute(this.metricsAlertEventProcessor);

		logger.info("start metrics analyze thread");
		ExecutorService metricsAnalyzeExecutor = ThreadPoolUtil.newSingleThreadExecutor("metrics-analyze-pool");
		metricsAnalyzeExecutor.execute(this.metricsAnalyzeEventProcessor);
		
		logger.info("start metrics store thread");
		ExecutorService metricsStoreExecutor = ThreadPoolUtil.newSingleThreadExecutor("metrics-store-pool");
		metricsStoreExecutor.execute(this.metricsStorageEventProcessor);

		start = true;
	}
	
	/**
	 * Publish message to ringbuffer
	 */
	@Override
	public void publish(LogEntry logEntry) {
		String category = logEntry.getCategory();
		
		// handle trace message
		if (category.equals(LogEntryCategory.TRACE)) {
			publishTrace(logEntry.getMessage());
		} 

		// handle metrics message
		if (category.equals(LogEntryCategory.METRICS)){
			publishMetrics(logEntry.getMessage());
		}
		
	}
	
	/**
	 * Publish trace to {@code TraceRingBuffer}.
	 * 
	 * @param msg
	 */
	private void publishTrace(String msg) {
		Span span = LogEntryCodec.decodeToSpan(msg);
		if (start && span != null) {
			
			/*
			 * validate span message
			 */
			span = MessageValidater.getMessageValidater().validateMessage(span);
			
			/*
			 * publish span to ringbuffer
			 */
			long sequence = this.traceRingBuffer.next();
			this.traceRingBuffer.get(sequence).setSpan(span);
			this.traceRingBuffer.publish(sequence);
		}
	}
	
	/**
	 * Publish metrics to {@code MetricsRingBuffer}.
	 * 
	 * @param msg
	 */
	private void publishMetrics(String msg) {
		HashMap<String, Object> metrics = LogEntryCodec.decodeToMap(msg);
		if (start && metrics != null) {
			
			/*
			 * validat metrics message 
			 */
			metrics = MessageValidater.getMessageValidater().validateMessage(metrics);
			
			/*
			 * publish metrics to ringbuffer
			 */
			long sequence = this.metricsRingBuffer.next();
			this.metricsRingBuffer.get(sequence).setResult(metrics);
			this.metricsRingBuffer.publish(sequence);
		}
	}
	
	/**
	 * Stop all event processors.
	 */
	@Override
	public void shutdown() {
		
		/*
		 * close trace process thread
		 */
		traceAlertEventProcessor.halt();
		traceAnalyzeEventProcessor.halt();
		traceStorageEventProcessor.halt();
		
		/*
		 * close metrics process thread
		 */
		metricsAlertEventProcessor.halt();
		metricsAnalyzeEventProcessor.halt();
		metricsStorageEventProcessor.halt();
		
	}

}
