package com.vipshop.microscope.trace.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Timer;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.metrics.jvm.JVMMetrics;

/**
 * Collect metrics data API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsStats {
	
	private static final MetricRegistry metrics = MetricsHolder.getMetricRegistry();
	
	private static volatile boolean start = false;
	
	/**
	 * Start MicroscopeReporter with default period and time.
	 */
	public static void start() {
		if (Tracer.isTraceEnable() && !start) {
			ScheduledReporter reporter = MicroscopeReporter.forRegistry(metrics).build();
			reporter.start(5, TimeUnit.SECONDS);
			start = true;
		}
	}
	
	/**
	 * Start MicroscopeReporter with given period and time.
	 */
	public static void start(long period, TimeUnit unit) {
		if (Tracer.isTraceEnable() && !start) {
			ScheduledReporter reporter = MicroscopeReporter.forRegistry(metrics).build();
			reporter.start(period, unit);
			start = true;
		}
	}

	/**
	 * Start ConsoleReporter with default period and time.
	 */
	public static void startLocal() {
		if (!start) {
			ScheduledReporter reporter = ConsoleReporter.forRegistry(metrics).build();
			reporter.start(5, TimeUnit.SECONDS);
			start = true;
		}
	}
	
	/**
	 * Start ConsoleReporter with default period and time.
	 */
	public static void startLocal(long period, TimeUnit unit) {
		if (!start) {
			ScheduledReporter reporter = ConsoleReporter.forRegistry(metrics).build();
			reporter.start(period, unit);
			start = true;
		}
	}
	
    /**
     * Given a {@link Metric}, registers it under the given name.
     *
     * @param name   the name of the metric
     * @param metric the metric
     * @param <T>    the type of the metric
     * @return {@code metric}
     * @throws IllegalArgumentException if the name is already registered
     */
	public static <T extends Metric> T register(String name, T metric) {
		return metrics.register(name, metric);
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
	 * Decrement the counter by n.
	 * 
	 * @param name
	 *            counter name
	 * @param n
	 *            decrement
	 */
	public static void dec(String name, long n) {
		metrics.counter(name).dec(n);
	}

    /**
     * Creates a new {@link Histogram} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link Histogram}
     */
	public static Histogram histogram(String name) {
		return metrics.histogram(name);
	}

    /**
     * Creates a new {@link Timer} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link Timer}
     */
	public static Meter meter(String name) {
		return metrics.meter(name);
	}

    /**
     * Creates a new {@link Timer} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link Timer}
     */
	public static Timer timer(String name) {
		return metrics.timer(name);
	}
	
}
