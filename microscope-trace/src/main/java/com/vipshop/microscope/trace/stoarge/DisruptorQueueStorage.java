package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.transport.Transporter;

public class DisruptorQueueStorage implements Storage, Transporter {
	
	/**
	 * LogEntry Event.
	 * 
	 * @author Xu Fei
	 * @version 1.0
	 */
	static class LogEntryEvent {
		public LogEntry logEntry;

		public LogEntry getLogEntry() {
			return logEntry;
		}
		
		public void setLogEntry(LogEntry logEntry) {
			this.logEntry = logEntry;
		}

		public final static EventFactory<LogEntryEvent> EVENT_FACTORY = new EventFactory<LogEntryEvent>() {
			public LogEntryEvent newInstance() {
				return new LogEntryEvent();
			}
		};
	}
	
	/**
	 * LogEntry send processor.
	 * 
	 * @author Xu Fei
	 * @version 1.0
	 */
	static class LogEntryEventHandler implements EventHandler<LogEntryEvent> {

		@Override
		public void onEvent(LogEntryEvent event, long sequence, boolean endOfBatch) throws Exception {
			
		}
		
	}
	
	private final int LOGENTRY_BUFFER_SIZE = 1024 * 8 * 1 * 1;
	
	/**
	 * LogEntry RingBuffer
	 */
	private final RingBuffer<LogEntryEvent> logEntryRingBuffer;
	private final SequenceBarrier logEntrySequenceBarrier;
	private final BatchEventProcessor<LogEntryEvent> logEntryEventProcessor;
	
	public DisruptorQueueStorage() {
		this.logEntryRingBuffer = RingBuffer.createSingleProducer(LogEntryEvent.EVENT_FACTORY, LOGENTRY_BUFFER_SIZE, new SleepingWaitStrategy());
		this.logEntrySequenceBarrier = logEntryRingBuffer.newBarrier();
		this.logEntryEventProcessor = new BatchEventProcessor<LogEntryEvent>(logEntryRingBuffer, logEntrySequenceBarrier, new LogEntryEventHandler());
	}

	@Override
	public LogEntry poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void transport() {
		ExecutorService logEntryExecutor = ThreadPoolUtil.newSingleDaemonThreadExecutor("disruptor-transporter");
		logEntryExecutor.execute(this.logEntryEventProcessor);
	}

	@Override
	public void add(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMetrics(HashMap<String, Object> metrics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSpan(Span span) {
		// TODO Auto-generated method stub
		
	}

}
