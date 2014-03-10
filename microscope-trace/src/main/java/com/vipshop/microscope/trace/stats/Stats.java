package com.vipshop.microscope.trace.stats;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.vipshop.microscope.trace.metrics.MetricsReporter;

/**
 * Collect performance data API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class Stats {
	
	private static final MetricRegistry metrics = new MetricRegistry();
	
	private static MetricsReporter reporter = MetricsReporter.forRegistry(metrics).build();

	static {
		start();
	}

	public static void start() {
		reporter.start(1, TimeUnit.SECONDS);
	}
	
	public static void start(long period, TimeUnit unit) {
		reporter.start(period, unit);
	}
	
	public static Counter getCounter(String name) {
		return metrics.counter(name);
	}

	public static Counter getCounter(Class<?> klass, String... names) {
		return metrics.counter(name(klass, names));
	}
	
	public static void registThread() {
		metrics.register("Thread", new ThreadStatesGaugeSet());
	}
	
	public static void registMemory() {
		metrics.register("Memory", new MemoryUsageGaugeSet());
	}
	
	public void inc(String name){
		metrics.counter(name).inc();
	}
	
	public void dec(String name) {
		metrics.counter(name).dec();
	}
	
}
