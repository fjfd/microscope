package com.vipshop.microscope.trace.stats;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricRegistry;
import com.vipshop.microscope.trace.metrics.CounterMetrics;
import com.vipshop.microscope.trace.metrics.ExceptionMetrics;
import com.vipshop.microscope.trace.metrics.JVMMetrics;
import com.vipshop.microscope.trace.metrics.MetricsContainer;
import com.vipshop.microscope.trace.metrics.MetricsReporter;

/**
 * Collect data API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class Stats {
	
	private static final MetricRegistry metrics = MetricsContainer.getMetricRegistry();
	
	static {
		MetricsReporter reporter = MetricsReporter.forRegistry(metrics).build();
		reporter.start(1, TimeUnit.SECONDS);
	}
	
	/**
	 * Collect JVM data 1 second a time.
	 */
	public static void statsJVM() {
		JVMMetrics.registerJVM();
	}
	
	/**
	 * Collect JVM data by given time.
	 * 
	 * @param period send period
	 * @param unit   timeunit
	 */
	public static void statsJVM(long period, TimeUnit unit) {
		JVMMetrics.registerJVM(period, unit);
	}
	
	/**
	 * Collect exception info.
	 * 
	 * @param t
	 */
	public static void statsException(Throwable t) {
		ExceptionMetrics.record(t);
	}
	
	/**
	 * Collect exception and debug info.
	 * 
	 * @param t
	 * @param info
	 */
	public static void statsException(Throwable t, String info) {
		ExceptionMetrics.record(t, info);
	}
	
	public static void inc(String name) {
		CounterMetrics.getCounter(name).inc();
	}
	
	public static void inc(String name, long n) {
		CounterMetrics.getCounter(name).inc(n);
	}
	
	public static void inc(Class<?> klass, String name) {
		CounterMetrics.getCounter(klass, name).inc();
	}
	
	public static void inc(Class<?> klass, String name, long n) {
		CounterMetrics.getCounter(klass, name).inc(n);
	}
	
	public static void dec(String name) {
		CounterMetrics.getCounter(name).dec();
	}
	
	public static void dec(String name, long n) {
		CounterMetrics.getCounter(name).dec(n);
	}
	
	public static void dec(Class<?> klass, String name) {
		CounterMetrics.getCounter(klass, name).dec();
	}
	
	public static void dec(Class<?> klass, String name, long n) {
		CounterMetrics.getCounter(klass, name).dec();
	}

}
