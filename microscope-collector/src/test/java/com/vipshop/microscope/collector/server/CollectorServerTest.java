package com.vipshop.microscope.collector.server;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.micorscope.framework.span.Codec;
import com.vipshop.micorscope.framework.thrift.LogEntry;
import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.thrift.ThriftCategory;
import com.vipshop.micorscope.framework.thrift.ThriftClient;
import com.vipshop.micorscope.framework.util.SpanMockUtil;
import com.vipshop.micorscope.framework.util.ThreadPoolUtil;
import com.vipshop.microscope.collector.consumer.DisruptorMessageConsumer;
import com.vipshop.microscope.collector.consumer.MessageConsumer;
import com.vipshop.microscope.collector.disruptor.SpanEvent;
import com.vipshop.microscope.storage.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.HbaseRepository;

public class CollectorServerTest {

	@BeforeClass
	public void setUp() {
		new Thread(new CollectorServer()).start();
		HbaseRepository.reinit();
	}

	@Test
	public void testCollectorServer() throws TException, InterruptedException {
		LogEntry logEntry = new Codec().encodeToLogEntry(SpanMockUtil.mockSpan());

		new ThriftClient("localhost", 9410, 3000, ThriftCategory.THREAD_SELECTOR).send(Arrays.asList(logEntry));

		TimeUnit.SECONDS.sleep(1);

		List<Map<String, Object>> apps = HbaseRepository.findAll();
		for (Map<String, Object> map : apps) {
			Set<Entry<String, Object>> entry = map.entrySet();
			int size = 0;
			for (Entry<String, Object> entry2 : entry) {
				size++;
				if (size == 1) {
					Assert.assertEquals("app", entry2.getKey());
				}
				if (size == 2) {
					Assert.assertEquals("trace", entry2.getKey());
				}
			}
		}

		List<Span> spans = HbaseRepository.findSpanByTraceId("8053381312019065847");
		for (Span tmpspan : spans) {
			Assert.assertEquals("localhost", tmpspan.getAppIp());
		}

		List<TraceTable> tables = HbaseRepository.findByTraceId("8053381312019065847");
		for (TraceTable traceTable : tables) {
			Assert.assertEquals("appname", traceTable.getAppName());
		}
		
	}
	
	static class SimpleDisruptorMessageConsumer implements MessageConsumer {
		
		private static final Logger logger = LoggerFactory.getLogger(DisruptorMessageConsumer.class);

		private final int BUFFER_SIZE = 1024 * 8 * 8 * 8;
		
		private volatile boolean start = false;
		
		private final RingBuffer<SpanEvent> ringBuffer;
		
		private final SequenceBarrier sequenceBarrier;
		
		private final BatchEventProcessor<SpanEvent> alertEventProcessor;
		private final BatchEventProcessor<SpanEvent> analyzeEventProcessor;
		private final BatchEventProcessor<SpanEvent> storageEventProcessor;
		
		public SimpleDisruptorMessageConsumer() {
			this.ringBuffer = RingBuffer.createSingleProducer(SpanEvent.EVENT_FACTORY, BUFFER_SIZE, new SleepingWaitStrategy());
			
			this.sequenceBarrier = ringBuffer.newBarrier();
			
			this.alertEventProcessor = new BatchEventProcessor<SpanEvent>(ringBuffer, sequenceBarrier, new EventHandler<SpanEvent>() {

				@Override
				public void onEvent(SpanEvent event, long sequence, boolean endOfBatch) throws Exception {
					event.getSpan();
				}
				
			});
			this.analyzeEventProcessor = new BatchEventProcessor<SpanEvent>(ringBuffer, sequenceBarrier, new EventHandler<SpanEvent>() {

				@Override
				public void onEvent(SpanEvent event, long sequence, boolean endOfBatch) throws Exception {
					event.getSpan();
				}
				
			});
			this.storageEventProcessor = new BatchEventProcessor<SpanEvent>(ringBuffer, sequenceBarrier, new EventHandler<SpanEvent>() {

				@Override
				public void onEvent(SpanEvent event, long sequence, boolean endOfBatch) throws Exception {
					event.getSpan();
				}
				
			});
			
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
	
	@Test
	public void testDisruptorPerf() {
		
	}
}
