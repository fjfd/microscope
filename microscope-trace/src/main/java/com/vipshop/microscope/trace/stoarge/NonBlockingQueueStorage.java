package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheck.Result;
import com.vipshop.microscope.common.logentry.LogEntry;

public class NonBlockingQueueStorage implements Storage {

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
	public void addHealthCheck(Map<String, Result> results, long date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Object object) {
		// TODO Auto-generated method stub
		
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

}
