package com.vipshop.microscope.trace.transport;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.micorscope.framework.span.MessageCodec;
import com.vipshop.micorscope.framework.util.ThreadPoolUtil;
import com.vipshop.microscope.thrift.client.ThriftClient;
import com.vipshop.microscope.thrift.gen.Span;
import com.vipshop.microscope.thrift.server.ThriftCategory;
import com.vipshop.microscope.trace.Constant;

/**
 * Use a {@code Disruptor} transport message to collector.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class DisruptorTransporter  {
	
	private static final Logger logger = LoggerFactory.getLogger(DisruptorTransporter.class);

	private static final int BUFFER_SIZE = 1024 * 8 * 8 * 8;
	
	private volatile boolean start = false;
	
	private static final MessageCodec encode = new MessageCodec();
	
	static class SpanEvent {

		private Span span;

		public Span getSpan() {
			return span;
		}

		public void setSpan(Span span) {
			this.span = span;
		}

		public final static EventFactory<SpanEvent> EVENT_FACTORY = new EventFactory<SpanEvent>() {
			public SpanEvent newInstance() {
				return new SpanEvent();
			}
		};
	}
	
	static class SendEventHandler implements EventHandler<SpanEvent> {
		@Override
		public void onEvent(SpanEvent event, long sequence, boolean endOfBatch) throws Exception {
			client.send(Arrays.asList(encode.encodeToLogEntry(event.getSpan())));
		}
	}
	
	/**
	 * thrift client
	 */
	private static final ThriftClient client = new ThriftClient(Constant.COLLECTOR_HOST, 
														 Constant.COLLECTOR_PORT, 
														 Constant.RECONNECT_WAIT_TIME,
														 ThriftCategory.THREAD_SELECTOR);

	
	private final RingBuffer<SpanEvent> ringBuffer;
	
	private final SequenceBarrier sequenceBarrier;
	
	private final SendEventHandler sendHandler;
	private final BatchEventProcessor<SpanEvent> sendEventProcessor;
	
	public DisruptorTransporter() {
		ringBuffer = RingBuffer.createSingleProducer(SpanEvent.EVENT_FACTORY, BUFFER_SIZE, new SleepingWaitStrategy());
		sequenceBarrier = ringBuffer.newBarrier();
		
		sendHandler = new SendEventHandler();
		sendEventProcessor = new BatchEventProcessor<SpanEvent>(ringBuffer, sequenceBarrier, sendHandler);

		ringBuffer.addGatingSequences(sendEventProcessor.getSequence());
	}
	
	public void start() {
		logger.info("send span to server base on disruptor ");
		
		ExecutorService executor = ThreadPoolUtil.newSingleDaemonThreadExecutor("transporter-pool");
		executor.execute(this.sendEventProcessor);
	}
	
	public void publish(Span span) {
		if (start && span != null) {
			long sequence = this.ringBuffer.next();
			this.ringBuffer.get(sequence).setSpan(span);
			this.ringBuffer.publish(sequence);
		}
	}
	
	public void shutdown() {
		sendEventProcessor.halt();
	}

	
}
