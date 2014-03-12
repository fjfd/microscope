package com.vipshop.microscope.trace.stats;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricRegistry;
import com.vipshop.microscope.trace.Tracer;
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
		if (Tracer.isTraceEnable()) {
			MetricsReporter reporter = MetricsReporter.forRegistry(metrics).build();
			reporter.start(10, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Collect JVM data.
	 */
	public static void statsJVM() {
		JVMMetrics.registerJVM();
	}
	
	/**
	 * Collect exception data.
	 * 
	 * @param t
	 */
	public static void statsException(Throwable t) {
		ExceptionMetrics.record(t);
	}
	
	/**
	 * Collect exception and debug data.
	 * 
	 * @param t
	 * @param info
	 */
	public static void statsException(Throwable t, String info) {
		ExceptionMetrics.record(t, info);
	}
	
	/**
	 * Increment the counter by one.
	 * 
	 * @param name the counter name
	 */
	public static void inc(String name) {
		CounterMetrics.getCounter(name).inc();
	}
	
	/**
	 * Increment the counter by {@code n}
	 * 
	 * @param name the counter name
	 * @param n    the increment
	 */
	public static void inc(String name, long n) {
		CounterMetrics.getCounter(name).inc(n);
	}
	
	/**
	 * Increment the counter by one
	 * 
	 * @param klass class name 
	 * @param name  counter name
	 */
	public static void inc(Class<?> klass, String name) {
		CounterMetrics.getCounter(klass, name).inc();
	}
	
	/**
	 * Increment the counter by {@code n}
	 * 
	 * @param klass class name
	 * @param name  counter name
	 * @param n     the increment
	 */
	public static void inc(Class<?> klass, String name, long n) {
		CounterMetrics.getCounter(klass, name).inc(n);
	}
	
	/**
	 * Decrement the counter by one.
	 * 
	 * @param name counter name
	 */
	public static void dec(String name) {
		CounterMetrics.getCounter(name).dec();
	}
	
	/**
	 * Decrement the counter by one.
	 * 
	 * @param name counter name
	 * @param n    decrement
	 */
	public static void dec(String name, long n) {
		CounterMetrics.getCounter(name).dec(n);
	}
	
	/**
	 * Decrement the counter by one.
	 * 
	 * @param klass class name
	 * @param name  decrement
	 */
	public static void dec(Class<?> klass, String name) {
		CounterMetrics.getCounter(klass, name).dec();
	}
	
	/**
	 * Decrement the counter by one.
	 * 
	 * @param klass class name
	 * @param name  counter name
	 * @param n     decrement
	 */
	public static void dec(Class<?> klass, String name, long n) {
		CounterMetrics.getCounter(klass, name).dec();
	}

}
