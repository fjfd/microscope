package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.concurrent.ExecutorService;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
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
	
	private volatile boolean start = false;
	
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
		this.start = true;
	}

	@Override
	public void addSpan(Span span) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addException(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCounter(SortedMap<String, Counter> counters, long date) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addGauge(SortedMap<String, Gauge> gauges, long date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addHistogram(SortedMap<String, Histogram> histograms, long date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeter(SortedMap<String, Meter> meters, long date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTimer(SortedMap<String, Timer> timers, long date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(LogEntry logEntry) {
		if (start && logEntry != null) {
			long sequence = this.logEntryRingBuffer.next();
			this.logEntryRingBuffer.get(sequence).setLogEntry(logEntry);
			this.logEntryRingBuffer.publish(sequence);
		}
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

}
