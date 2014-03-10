package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.vipshop.microscope.common.logentry.Codec;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.trace.Tracer;

/**
 *  A {@link BlockingQueue} store spans in client memory.
 *  
 * @author Xu Fei
 * @version 1.0
 */
public class QueueStorage implements Storage {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueStorage.class);
	
	private static final BlockingQueue<LogEntry> queue = new ArrayBlockingQueue<LogEntry>(Tracer.QUEUE_SIZE);
	
	public static class QueueStorageHolder {
		private static final QueueStorage storage = new QueueStorage();
	}
	
	public static QueueStorage getStorage() {
		return QueueStorageHolder.storage;
	}
	
	private QueueStorage() {
		
	}
	
	/**
	 * Add Span to queue.
	 * 
	 * @param span
	 */
	public void add(Span span) {
		LogEntry logEntry = Codec.encodeToLogEntry(span);
		add(logEntry);
	}
	
	/**
	 * Add Exception map to queue.
	 * 
	 */
	public void add(HashMap<String, Object> map) {
		LogEntry logEntry = Codec.encodeToLogEntry(map);
		add(logEntry);
	}
	
	public void addCounter(SortedMap<String, Counter> counters, String date) {
		StringBuilder builder = new StringBuilder();
		builder.append("date=").append(date).append(":");
		for (Entry<String, Counter> entry : counters.entrySet()) {
			builder.append(entry.getKey()).append("=");
			builder.append(entry.getValue().getCount()).append(":");
		}
		
		String counter = builder.toString();
		add(counter.substring(0, counter.length() - 1));
			   
	}
	
	@SuppressWarnings("rawtypes")
	public void addGauge(SortedMap<String, Gauge> gauges, String date) {
		StringBuilder builder = new StringBuilder();
		builder.append("date=").append(date).append(":");
		for (Entry<String, Gauge> entry : gauges.entrySet()) {
			builder.append(entry.getKey()).append("=");
			builder.append(entry.getValue().getValue()).append(":");
		}
		
		String gauge = builder.toString();
		add(gauge.substring(0, gauge.length() - 1));
			   
	}
	
	public void add(String msg) {
		LogEntry logEntry = Codec.encodeToLogEntry(msg);
		add(logEntry);
	}

	/**
	 * Add LogEntry to queue.
	 * 
	 * If client queue is full, empty queue.
	 * 
	 * @param span {@link Span}
	 */
	public void add(LogEntry logEntry) { 
		if (logEntry == null) {
			return;
		}
		
		boolean isFull = !queue.offer(logEntry);
		
		if (isFull) {
			queue.clear();
			logger.info("client queue is full, clean queue now");
		}
	}
	
	/**
	 * Get span from queue.
	 * 
	 * @return {@link Span}
	 */
	public LogEntry poll() {
		return queue.poll();
	}
	
	/**
	 * Get size of queue.
	 */
	public int size() {
		return queue.size();
	}
}
