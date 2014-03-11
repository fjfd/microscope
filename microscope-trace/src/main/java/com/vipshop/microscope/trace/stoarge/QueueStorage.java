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
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
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
		private static final Storage storage = new QueueStorage();
	}
	
	public static Storage getStorage() {
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
	
	public void add(String msg) {
		LogEntry logEntry = Codec.encodeToLogEntry(msg);
		add(logEntry);
	}

	/**
	 * Add exception metrics to queue.
	 * 
	 */
	public void addException(HashMap<String, Object> map) {
		StringBuilder builder = new StringBuilder();
		builder.append("metrics.type=").append("exception:");
		builder.append("stack=").append(Codec.encodeToString(map));
		add(builder.toString());
	}
	
	/**
	 * 
	 * @param counters
	 * @param date
	 */
	public void addCounter(SortedMap<String, Counter> counters, long date) {
		StringBuilder builder = new StringBuilder();
		builder.append("metrics.type=").append("counter:");
		builder.append("date=").append(date).append(":");
		for (Entry<String, Counter> entry : counters.entrySet()) {
			builder.append(entry.getKey()).append("=");
			builder.append(entry.getValue().getCount()).append(":");
		}
		
		String counter = builder.toString();
		add(counter.substring(0, counter.length() - 1));
			   
	}
	
	@SuppressWarnings("rawtypes")
	public void addGauge(SortedMap<String, Gauge> gauges, long date) {
		StringBuilder builder = new StringBuilder();
		builder.append("metrics.type=").append("gauge:");
		builder.append("date=").append(date).append(":");
		for (Entry<String, Gauge> entry : gauges.entrySet()) {
			builder.append(entry.getKey()).append("=");
			builder.append(entry.getValue().getValue()).append(":");
		}
		
		String gauge = builder.toString();
		add(gauge.substring(0, gauge.length() - 1));
			   
	}
	
	public void addHistogram(SortedMap<String, Histogram> histograms, long date) {
		StringBuilder builder = new StringBuilder();
		builder.append("metrics.type=").append("histogram:");
		builder.append("date=").append(date).append(":");
		for (Map.Entry<String, Histogram> entry : histograms.entrySet()) {
			builder.append(entry.getKey()).append("=");
			
			Histogram histogram = entry.getValue();
			Snapshot snapshot = histogram.getSnapshot();
			StringBuilder hBuilder = new StringBuilder();
			hBuilder.append("count;").append(histogram.getCount()).append(";");
			hBuilder.append("min;").append(snapshot.getMin()).append(";");
			hBuilder.append("max;").append(snapshot.getMax()).append(";");
			hBuilder.append("mean;").append(snapshot.getMean()).append(";");
			hBuilder.append("stddev;").append(snapshot.getStdDev()).append(";");
			hBuilder.append("median;").append(snapshot.getMedian()).append(";");
			hBuilder.append("75%%;").append(snapshot.get75thPercentile()).append(";");
			hBuilder.append("95%%;").append(snapshot.get95thPercentile()).append(";");
			hBuilder.append("98%%;").append(snapshot.get98thPercentile()).append(";");
			hBuilder.append("99%%;").append(snapshot.get99thPercentile()).append(";");
			hBuilder.append("99.9%%;").append(snapshot.get999thPercentile()).append(";");
			builder.append(hBuilder.toString()).append(":");
		}
		
		String histogram = builder.toString();
		add(histogram.substring(0, histogram.length() - 1));
	}
	
	public void addMeter(SortedMap<String, Meter> meters, long date) {
		StringBuilder builder = new StringBuilder();
		builder.append("metrics.type=").append("meter:");
		builder.append("date=").append(date).append(":");
		for (Map.Entry<String, Meter> entry : meters.entrySet()) {
			builder.append(entry.getKey()).append("=");
		}
	}
	
	public void addTimer(SortedMap<String, Timer> timers, long date) {
		
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
