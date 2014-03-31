package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.codahale.metrics.health.HealthCheck.Result;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.logentry.LogEntryCodec;
import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.util.IPAddressUtil;
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
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", MetricsCategory.Exception);
		metrics.put("stack", map);
		add(metrics);
	}
	
	/**
	 * Add counter metrics.
	 * 
	 * @param counters
	 * @param date
	 */
	public void addCounter(SortedMap<String, Counter> counters, long date) {
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", MetricsCategory.Counter);
		metrics.put("date", date);
		metrics.put("app", Tracer.APP_NAME);
		metrics.put("ip", IPAddressUtil.IPAddress());

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
		HashMap<String, Object> metrics = new LinkedHashMap<String, Object>();
		
		metrics.put("type", MetricsCategory.Gauge);
		metrics.put("date", date);
		metrics.put("app", Tracer.APP_NAME);
		metrics.put("ip", IPAddressUtil.IPAddress());
		
		for (Entry<String, Gauge> entry : gauges.entrySet()) {
			metrics.put(entry.getKey(), entry.getValue().getValue());
		}
		add(metrics);
	}
	
	/**
	 * Add histogram metrics
	 */
	public void addHistogram(SortedMap<String, Histogram> histograms, long date) {
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", MetricsCategory.Histogram);
		metrics.put("date", date);
		metrics.put("app", Tracer.APP_NAME);
		metrics.put("ip", IPAddressUtil.IPAddress());

		for (Map.Entry<String, Histogram> entry : histograms.entrySet()) {
			metrics.put(entry.getKey(), entry.getValue());
		}
		add(metrics);
	}
	
	/**
	 * Add meter metrics.
	 */
	public void addMeter(SortedMap<String, Meter> meters, long date) {
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", MetricsCategory.Meter);
		metrics.put("date", date);
		metrics.put("app", Tracer.APP_NAME);
		metrics.put("ip", IPAddressUtil.IPAddress());

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
		metrics.put("type", MetricsCategory.Timer);
		metrics.put("date", date);
		metrics.put("app", Tracer.APP_NAME);
		metrics.put("ip", IPAddressUtil.IPAddress());

		for (Map.Entry<String, Timer> entry : timers.entrySet()) {
			metrics.put(entry.getKey(), entry.getValue());
		}
		add(metrics);
	}
	
	/**
	 * Add health check result
	 */
	@Override
	public void addHealthCheck(Map<String, Result> results, long date) {
		HashMap<String, Object> metrics = new HashMap<String, Object>();
		metrics.put("type", MetricsCategory.Health);
		metrics.put("date", date);
		metrics.put("app", Tracer.APP_NAME);
		metrics.put("ip", IPAddressUtil.IPAddress());
		metrics.put("result", results);
		
		add(metrics);
		
//		for (Entry<String, HealthCheck.Result> entry : results.entrySet()) {
//	    if (entry.getValue().isHealthy()) {
//	        System.out.println(entry.getKey() + " is healthy");
//	    } else {
//	        System.err.println(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
//	        final Throwable e = entry.getValue().getError();
//	        if (e != null) {
//	            e.printStackTrace();
//	        }
//	    }
//	}

	}

	/**
	 * Put object to queue.
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
	@SuppressWarnings("unchecked")
	public LogEntry poll() {
		Object object = queue.poll();
		
		if (object instanceof Span) {
			LogEntry logEntry = null;
			try {
				logEntry = LogEntryCodec.encodeToLogEntry((Span)object);
			} catch (Exception e) {
				logger.debug("encode span to logEntry error", e);
				return null;
			}
			return logEntry;
		}
		
		if (object instanceof HashMap) {
			LogEntry logEntry = null;
			try {
				logEntry = LogEntryCodec.encodeToLogEntry((HashMap<String, Object>)object);
			} catch (Exception e) {
				logger.debug("encode hashmap to logEntry error", e);
				return null;
			}
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
