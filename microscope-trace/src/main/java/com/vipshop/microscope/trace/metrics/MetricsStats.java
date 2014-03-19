package com.vipshop.microscope.trace.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricRegistry;
import com.vipshop.microscope.trace.Tracer;

/**
 * Collect data API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsStats {

	private static final MetricRegistry metrics = MetricsHolder.getMetricRegistry();

	static {
		if (Tracer.isTraceEnable()) {
			MetricsReporter reporter = MetricsReporter.forRegistry(metrics).build();
			reporter.start(1, TimeUnit.SECONDS);
		}
	}

	/**
	 * Collect JVM data.
	 */
	public static void statsJVM() {
		JVMMetrics.registerJVM();
	}

	/**
	 * Increment the counter by one.
	 * 
	 * @param name
	 *            the counter name
	 */
	public static void inc(String name) {
		CounterMetrics.getCounter(name).inc();
	}

	/**
	 * Increment the counter by {@code n}
	 * 
	 * @param name
	 *            the counter name
	 * @param n
	 *            the increment
	 */
	public static void inc(String name, long n) {
		CounterMetrics.getCounter(name).inc(n);
	}

	/**
	 * Increment the counter by one
	 * 
	 * @param klass
	 *            class name
	 * @param name
	 *            counter name
	 */
	public static void inc(Class<?> klass, String name) {
		CounterMetrics.getCounter(klass, name).inc();
	}

	/**
	 * Increment the counter by {@code n}
	 * 
	 * @param klass
	 *            class name
	 * @param name
	 *            counter name
	 * @param n
	 *            the increment
	 */
	public static void inc(Class<?> klass, String name, long n) {
		CounterMetrics.getCounter(klass, name).inc(n);
	}

	/**
	 * Decrement the counter by one.
	 * 
	 * @param name
	 *            counter name
	 */
	public static void dec(String name) {
		CounterMetrics.getCounter(name).dec();
	}

	/**
	 * Decrement the counter by one.
	 * 
	 * @param name
	 *            counter name
	 * @param n
	 *            decrement
	 */
	public static void dec(String name, long n) {
		CounterMetrics.getCounter(name).dec(n);
	}

	/**
	 * Decrement the counter by one.
	 * 
	 * @param klass
	 *            class name
	 * @param name
	 *            decrement
	 */
	public static void dec(Class<?> klass, String name) {
		CounterMetrics.getCounter(klass, name).dec();
	}

	/**
	 * Decrement the counter by n.
	 * 
	 * @param klass
	 *            class name
	 * @param name
	 *            counter name
	 * @param n
	 *            decrement
	 */
	public static void dec(Class<?> klass, String name, long n) {
		CounterMetrics.getCounter(klass, name).dec();
	}

}
