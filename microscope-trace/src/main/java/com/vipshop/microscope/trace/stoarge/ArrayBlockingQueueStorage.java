package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.vipshop.microscope.common.logentry.Codec;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.trace.Tracer;

/**
 * Storge metrics in client use {@code ArrayBlockingQueue}.
 *  
 * @author Xu Fei
 * @version 1.0
 */
public class ArrayBlockingQueueStorage implements Storage {
	
	private static final Logger logger = LoggerFactory.getLogger(ArrayBlockingQueueStorage.class);
	
	private static final BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(Tracer.QUEUE_SIZE);
	
	/**
	 * Package access construct
	 */
	ArrayBlockingQueueStorage() {}

	/**
	 * Add exception metrics.
	 * 
	 */
	public void addException(HashMap<String, Object> map) {
		StringBuilder builder = new StringBuilder();
		builder.append("exception:");
		builder.append("stack=").append(Codec.encodeToString(map));
		add(builder.toString());
	}
	
	/**
	 * Add counter metrics.
	 * 
	 * @param counters
	 * @param date
	 */
	public void addCounter(SortedMap<String, Counter> counters, long date) {
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", "counter");
		metrics.put("date", date);
		for (Entry<String, Counter> entry : counters.entrySet()) {
			metrics.put(entry.getKey(), entry.getValue().getCount());
		}
		add(metrics);
	}
	
	/**
	 * Add guage metrics.
	 */
	@SuppressWarnings("rawtypes")
	public void addGauge(SortedMap<String, Gauge> gauges, long date) {
		HashMap<String, Object> threadMetrics = new HashMap<String, Object>();
		HashMap<String, Object> memoryMetrics = new HashMap<String, Object>();
		HashMap<String, Object> gcMetrics = new HashMap<String, Object>();
		
		threadMetrics.put("type", "thread");
		threadMetrics.put("date", date);
		
		memoryMetrics.put("type", "memory");
		memoryMetrics.put("date", date);
		
		gcMetrics.put("type", "gc");
		gcMetrics.put("date", date);
		
		for (Entry<String, Gauge> entry : gauges.entrySet()) {
			if (entry.getKey().startsWith("Thread")) {
				threadMetrics.put(entry.getKey(), entry.getValue().getValue());
				continue;
			}
			
			if (entry.getKey().startsWith("Thread")) {
				threadMetrics.put(entry.getKey(), entry.getValue().getValue());
				continue;
			}
			if (entry.getKey().startsWith("Thread")) {
				threadMetrics.put(entry.getKey(), entry.getValue().getValue());
				continue;
			}
		}
		
		add(threadMetrics);
		add(memoryMetrics);
		add(gcMetrics);
			   
	}
	
	/**
	 * Add histogram metrics
	 */
	public void addHistogram(SortedMap<String, Histogram> histograms, long date) {
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", "histogram");
		metrics.put("date", date);
		for (Map.Entry<String, Histogram> entry : histograms.entrySet()) {
			metrics.put(entry.getKey(), entry.getValue().getSnapshot());
		}
		add(metrics);
	}
	
	/**
	 * Add meter metrics.
	 */
	public void addMeter(SortedMap<String, Meter> meters, long date) {
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", "meter");
		metrics.put("date", date);
		for (Map.Entry<String, Meter> entry : meters.entrySet()) {
			metrics.put(entry.getKey(), entry.getValue());
		}
		add(metrics);
	}
	
	/**
	 * Add timer metrics
	 */
	public void addTimer(SortedMap<String, Timer> timers, long date) {
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", "timer");
		metrics.put("date", date);
		for (Map.Entry<String, Timer> entry : timers.entrySet()) {
			metrics.put(entry.getKey(), entry.getValue().getSnapshot());
		}
		add(metrics);
	}
	
	/**
	 * Add string metrics.
	 * 
	 * @param msg
	 */
	public void add(Object object) {
		boolean isFull = !queue.offer(object);
		
		if (isFull) {
			queue.clear();
			logger.info("client queue is full, clean queue now");
		}
	}
	
	/**
	 * Get logEntry from queue.
	 * 
	 * @return {@link Span}
	 */
	@Override
	public LogEntry poll() {
		Object object = queue.poll();
		
		if (object instanceof Span) {
			LogEntry logEntry = Codec.encodeToLogEntry((Span)object);
			return logEntry;
		}
		
		if (object instanceof HashMap) {
			@SuppressWarnings("unchecked")
			LogEntry logEntry = Codec.encodeToLogEntry((HashMap<String, Object>)object);
			return logEntry;
		}
		
		if (object instanceof String) {
			LogEntry logEntry = Codec.encodeToLogEntry((String)object);
			return logEntry;
		}
		
		return null;
	}
	
	/**
	 * Get size of queue.
	 */
	@Override
	public int size() {
		return queue.size();
	}

}
