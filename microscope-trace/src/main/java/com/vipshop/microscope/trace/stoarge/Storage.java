package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.SortedMap;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;

/**
 * Storge metrics in client queue.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {
	
	/**
	 * Trace metrics
	 * 
	 * @param span
	 */
	public void addSpan(Span span);
	
	/**
	 * Exception metrics
	 * 
	 * @param map
	 */
	public void addException(HashMap<String, Object> map);
	
	/**
	 * Counter metrics
	 * 
	 * @param counters
	 * @param date
	 */
	public void addCounter(SortedMap<String, Counter> counters, long date);
	
	/**
	 * Gauge metrics
	 * 
	 * @param gauges
	 * @param date
	 */
	@SuppressWarnings("rawtypes")
	public void addGauge(SortedMap<String, Gauge> gauges, long date);
	
	/**
	 * Histogram metrics
	 * 
	 * @param histograms
	 * @param date
	 */
	public void addHistogram(SortedMap<String, Histogram> histograms, long date);
	
	/**
	 * Meter metrics
	 * 
	 * @param meters
	 * @param date
	 */
	public void addMeter(SortedMap<String, Meter> meters, long date);
	
	/**
	 * Timer metrics
	 * 
	 * @param timers
	 * @param date
	 */
	public void addTimer(SortedMap<String, Timer> timers, long date);
	
	/**
	 * Put LogEntry to queue
	 * 
	 * @param logEntry
	 */
	public void add(LogEntry logEntry);

	/**
	 * Get LogEntry from queue
	 * @return
	 */
	public LogEntry poll();
	
	/**
	 * Get size of queue
	 * 
	 * @return
	 */
	public int size();
	
}
