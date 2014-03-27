package com.vipshop.microscope.trace.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.vipshop.microscope.trace.metrics.jvm.JVMMetrics;

/**
 * Collect data API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsStats {

	private static final MetricRegistry metrics = MetricsHolder.getMetricRegistry();

	public static void start() {
		MetricsReporter reporter = MetricsReporter.forRegistry(metrics).build();
		reporter.start(5, TimeUnit.SECONDS);
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
		metrics.counter(name).inc();
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
		metrics.counter(name).inc(n);
	}

	/**
	 * Decrement the counter by one.
	 * 
	 * @param name
	 *            counter name
	 */
	public static void dec(String name) {
		metrics.counter(name).dec();
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
		metrics.counter(name).dec(n);
	}

	public static <T extends Metric> T register(String name, T metric) {
		return metrics.register(name, metric);
	}

	public static Histogram histogram(String name) {
		return metrics.histogram(name);
	}

	public static Meter meter(String name) {
		return metrics.meter(name);
	}

	public static Timer timer(String name) {
		return metrics.timer(name);
	}
}
