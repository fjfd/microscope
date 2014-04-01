package com.vipshop.microscope.trace.metrics;

import info.ganglia.gmetric4j.gmetric.GMetric;
import info.ganglia.gmetric4j.gmetric.GMetric.UDPAddressingMode;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.ganglia.GangliaReporter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.metrics.jvm.DiskMetricsSet;
import com.vipshop.microscope.trace.metrics.jvm.IOMetricsSet;
import com.vipshop.microscope.trace.metrics.jvm.OSMetricsSet;
import com.vipshop.microscope.trace.metrics.jvm.RuntimeMetricsSet;
import com.vipshop.microscope.trace.metrics.jvm.ThreadMetricsSet;

/**
 * Collect metrics data API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsStats {
	
	private static final MetricRegistry metrics = MetricsHolder.getMetricRegistry();
	private static final HealthCheckRegistry healthMetrics = MetricsHolder.getHealthCheckRegistry();
	
	private static volatile boolean start = false;
	
	/**
	 * Start MicroscopeReporter with default period and time.
	 */
	public static void startMicroscopeReporter() {
		if (Tracer.isTraceEnable() && !start) {
			ScheduledReporter reporter = MicroscopeReporter.forRegistry(metrics)
														   .convertRatesTo(TimeUnit.SECONDS)
														   .convertDurationsTo(TimeUnit.MILLISECONDS)
														   .build();
			reporter.start(5, TimeUnit.SECONDS);
			start = true;
		}
	}
	
	/**
	 * Start MicroscopeReporter with given period and time.
	 */
	public static void startMicroscopeReporter(long period, TimeUnit unit) {
		if (Tracer.isTraceEnable() && !start) {
			ScheduledReporter reporter = MicroscopeReporter.forRegistry(metrics)
					   									   .convertRatesTo(TimeUnit.SECONDS)
					   									   .convertDurationsTo(TimeUnit.MILLISECONDS)
					   									   .build();
			reporter.start(period, unit);
			start = true;
		}
	}

	/**
	 * Start Slf4jReporter with default period and time.
	 */
	public static void startSlf4jReporter() {
		if (!start) {
			ScheduledReporter reporter = Slf4jReporter.forRegistry(metrics)
													  .convertRatesTo(TimeUnit.SECONDS)
													  .convertDurationsTo(TimeUnit.MILLISECONDS)
													  .build();
			reporter.start(5, TimeUnit.SECONDS);
			start = true;
		}
	}
	
	/**
	 * Start Slf4jReporter with default period and time.
	 */
	public static void startSlf4jReporter(long period, TimeUnit unit) {
		if (!start) {
			ScheduledReporter reporter = Slf4jReporter.forRegistry(metrics)
												      .convertRatesTo(TimeUnit.SECONDS)
												      .convertDurationsTo(TimeUnit.MILLISECONDS)
												      .build();
			reporter.start(period, unit);
			start = true;
		}
	}
	
	/**
	 * Start ConsoleReporter with default period and time.
	 */
	public static void startConsoleReporter() {
		if (!start) {
			ScheduledReporter reporter = ConsoleReporter.forRegistry(metrics)
													    .convertRatesTo(TimeUnit.SECONDS)
													    .convertDurationsTo(TimeUnit.MILLISECONDS)
													    .build();
			reporter.start(5, TimeUnit.SECONDS);
			start = true;
		}
	}
	
	/**
	 * Start ConsoleReporter with default period and time.
	 */
	public static void startConsoleReporter(long period, TimeUnit unit) {
		if (!start) {
			ScheduledReporter reporter = ConsoleReporter.forRegistry(metrics)
				    								    .convertRatesTo(TimeUnit.SECONDS)
				    								    .convertDurationsTo(TimeUnit.MILLISECONDS)
				    								    .build();
			reporter.start(period, unit);
			start = true;
		}
	}
	
	/**
	 * Start CsvReporter with default period and time.
	 */
	public static void startCsvReporter() {
		if (!start) {
			ScheduledReporter reporter = CsvReporter.forRegistry(metrics).build(new File("."));
			reporter.start(5, TimeUnit.SECONDS);
			start = true;
		}
	}
	
	/**
	 * Start CsvReporter with default period and time.
	 */
	public static void startCsvReporter(long period, TimeUnit unit) {
		if (!start) {
			ScheduledReporter reporter = CsvReporter.forRegistry(metrics).build(new File("."));
			reporter.start(period, unit);
			start = true;
		}
	}
	
	/**
	 * Start jmx reporter
	 */
	public static void startJmxReporter() {
		if (!start) {
			JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
			reporter.start();
			start = true;
		}
	}
	
	/**
	 * Start GraphiteReporter
	 */
	public static void startGraphiteReporter() {
		if (!start) {
			final Graphite graphite = new Graphite(new InetSocketAddress("graphite.example.com", 2003));
			final GraphiteReporter reporter = GraphiteReporter.forRegistry(metrics)
															  .prefixedWith("web1.example.com")
															  .convertRatesTo(TimeUnit.SECONDS)
															  .convertDurationsTo(TimeUnit.MILLISECONDS)
															  .filter(MetricFilter.ALL)
															  .build(graphite);
			reporter.start(5, TimeUnit.MINUTES);
			start = true;
		}
	}
	
	/**
	 * Start GraphiteReporter
	 * 
	 * @param period
	 * @param unit
	 */
	public static void startGraphiteReporter(long period, TimeUnit unit) {
		if (!start) {
			final Graphite graphite = new Graphite(new InetSocketAddress("graphite.example.com", 2003));
			final GraphiteReporter reporter = GraphiteReporter.forRegistry(metrics)
					.prefixedWith("web1.example.com")
					.convertRatesTo(TimeUnit.SECONDS)
					.convertDurationsTo(TimeUnit.MILLISECONDS)
					.filter(MetricFilter.ALL)
					.build(graphite);
			reporter.start(period, unit);
			start = true;
		}
	}
	
	public static void startGangliaReporter() throws IOException {
		if (!start) {
			final GMetric ganglia = new GMetric("ganglia.example.com", 8649, UDPAddressingMode.MULTICAST, 1);
			final GangliaReporter reporter = GangliaReporter.forRegistry(metrics)
					.convertRatesTo(TimeUnit.SECONDS)
					.convertDurationsTo(TimeUnit.MILLISECONDS)
					.build(ganglia);
			reporter.start(5, TimeUnit.MINUTES);
			start = true;
		}
	}
	
	public static void startGangliaReporter(long period, TimeUnit unit) throws IOException {
		if (!start) {
			final GMetric ganglia = new GMetric("ganglia.example.com", 8649, UDPAddressingMode.MULTICAST, 1);
			final GangliaReporter reporter = GangliaReporter.forRegistry(metrics)
					.convertRatesTo(TimeUnit.SECONDS)
					.convertDurationsTo(TimeUnit.MILLISECONDS)
					.build(ganglia);
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
	public static void registerJVM() {
		metrics.register(MetricsCategory.JVM_Thread, new ThreadMetricsSet());
		metrics.register(MetricsCategory.JVM_Memory, new MemoryUsageGaugeSet());
		metrics.register(MetricsCategory.JVM_GC, new GarbageCollectorMetricSet());
		metrics.register(MetricsCategory.JVM_Runtime, new RuntimeMetricsSet());
		metrics.register(MetricsCategory.JVM_OS, new OSMetricsSet());
		metrics.register(MetricsCategory.JVM_IO, new IOMetricsSet());
		metrics.register(MetricsCategory.JVM_DISK, new DiskMetricsSet());
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
     * Creates a new {@link Counter} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link Counter}
     */
	public static Counter counter(String name) {
		return metrics.counter(name);
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
	
    /**
     * Registers an application {@link HealthCheck}.
     *
     * @param name        the name of the health check
     * @param healthCheck the {@link HealthCheck} instance
     */
	public static void register(String name, HealthCheck healthCheck) {
		healthMetrics.register(name, healthCheck);
	}
	
    /**
     * Unregisters the application {@link HealthCheck} with the given name.
     *
     * @param name the name of the {@link HealthCheck} instance
     */
	public static void unregister(String name) {
		healthMetrics.unregister(name);
	}

    /**
     * Runs the registered health checks and returns a map of the results.
     *
     * @return a map of the health check results
     */
	public static Map<String, HealthCheck.Result> runHealthChecks() {
		return healthMetrics.runHealthChecks();
	}
	
}
