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
 * Storge span in client.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {
	
	public void add(Span span);
	
	public void addException(HashMap<String, Object> map);
	
	public void addCounter(SortedMap<String, Counter> counters, long date);
	
	public void addGauge(@SuppressWarnings("rawtypes") SortedMap<String, Gauge> gauges, long date);
	
	public void addHistogram(SortedMap<String, Histogram> histograms, long date);
	
	public void addMeter(SortedMap<String, Meter> meters, long date);
	
	public void addTimer(SortedMap<String, Timer> timers, long date);
	
	public void add(LogEntry logEntry);

	public LogEntry poll();
	
	public int size();
}
