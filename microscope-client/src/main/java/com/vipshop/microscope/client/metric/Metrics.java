package com.vipshop.microscope.client.metric;

import com.codahale.metrics.*;
import com.codahale.metrics.Timer;
import com.codahale.metrics.ganglia.GangliaReporter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.cons.Constants;
import com.vipshop.microscope.client.Tracer;
import info.ganglia.gmetric4j.gmetric.GMetric;
import info.ganglia.gmetric4j.gmetric.GMetric.UDPAddressingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Metrics data build API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class Metrics {

    private static final Logger logger = LoggerFactory.getLogger(Metrics.class);

    private static final MetricRegistry metrics = MetricContainer.getMetricRegistry();
    private static final HealthCheckRegistry healthMetrics = MetricContainer.getHealthCheckRegistry();

    private static volatile boolean start = false;

    /**
     * Start MicroscopeReporter with default period and time.
     */
    public static void startMicroscopeReporter() {
        startMicroscopeReporter(Tracer.REPORT_PERIOD_TIME, TimeUnit.SECONDS);
    }

    /**
     * Start MicroscopeReporter with given period and time.
     */
    public static void startMicroscopeReporter(long period, TimeUnit unit) {
        if (Tracer.isTraceEnable() && !start) {

            Map<String, String> tags = new HashMap<String, String>();
            tags.put(Constants.APP, Tracer.APP_NAME);
            tags.put(Constants.IP, IPAddressUtil.IPAddress());

            ScheduledReporter reporter = MetricReporter.forRegistry(metrics)
                    .convertRatesTo(TimeUnit.SECONDS)
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .filter(MetricFilter.ALL)
                    .withTags(tags)
                    .build();

            logger.info("start microscope metrics reporter with period " + period + " second");

            reporter.start(period, unit);

            /**
             * register JVM metrics
             */
            Metrics.registerJVM();

        }
    }

    /**
     * Start Slf4jReporter with default period and time.
     */
    public static void startSlf4jReporter() {
        startSlf4jReporter(60, TimeUnit.SECONDS);
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
        startConsoleReporter(60, TimeUnit.SECONDS);
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
        startCsvReporter(60, TimeUnit.SECONDS);
    }

    /**
     * Start CsvReporter with default period and time.
     */
    public static void startCsvReporter(long period, TimeUnit unit) {
        if (!start) {
            ScheduledReporter reporter = CsvReporter.forRegistry(metrics).build(new File("./tmp"));
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
        startGraphiteReporter(60, TimeUnit.MINUTES);
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
        startGangliaReporter(60, TimeUnit.MINUTES);
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
     * Register JVM metrics.
     */
    public static void registerJVM() {

        logger.info("register JVM metrics");

        metrics.register(Constants.JVM, new JVMMetrics());
    }

    /**
     * Given a {@link MetricData}, registers it under the given name.
     *
     * @param name   the name of the metric
     * @param metric the metric
     * @return {@code metric}
     * @throws IllegalArgumentException if the name is already registered
     */
    public static <T extends Metric> Metric register(String name, T metric) {
        return metrics.register(name, metric);
    }

    /**
     * Given a metric set, registers them.
     *
     * @param metricset    a set of metrics
     * @throws IllegalArgumentException if any of the names are already registered
     */
    public void registerAll(MetricSet metricset) throws IllegalArgumentException {
        metrics.registerAll(metricset);
    }

    /**
     * Creates a new {@link com.codahale.metrics.Counter} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link com.codahale.metrics.Counter}
     */
    public static Counter counter(String name) {
        return metrics.counter(name);
    }

    /**
     * Increment the counter by one.
     *
     * @param name the counter name
     */
    public static void inc(String name) {
        metrics.counter(name).inc();
    }

    /**
     * Increment the counter by {@code n}
     *
     * @param name the counter name
     * @param n    the increment
     */
    public static void inc(String name, long n) {
        metrics.counter(name).inc(n);
    }

    /**
     * Decrement the counter by one.
     *
     * @param name counter name
     */
    public static void dec(String name) {
        metrics.counter(name).dec();
    }

    /**
     * Decrement the counter by n.
     *
     * @param name counter name
     * @param n    decrement
     */
    public static void dec(String name, long n) {
        metrics.counter(name).dec(n);
    }

    /**
     * Creates a new {@link com.codahale.metrics.Histogram} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link com.codahale.metrics.Histogram}
     */
    public static Histogram histogram(String name) {
        return metrics.histogram(name);
    }

    /**
     * Creates a new {@link com.codahale.metrics.Timer} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link com.codahale.metrics.Timer}
     */
    public static Meter meter(String name) {
        return metrics.meter(name);
    }

    /**
     * Creates a new {@link com.codahale.metrics.Timer} and registers it under the given name.
     *
     * @param name the name of the metric
     * @return a new {@link com.codahale.metrics.Timer}
     */
    public static Timer timer(String name) {
        return metrics.timer(name);
    }

    /**
     * Removes the metric with the given name.
     *
     * @param name the name of the metric
     * @return whether or not the metric was removed
     */
    public boolean remove(String name) {
        return metrics.remove(name);
    }

    /**
     * Removes all metrics which match the given filter.
     *
     * @param filter a filter
     */
    public void removeMatching(MetricFilter filter) {
        metrics.removeMatching(filter);
    }

    /**
     * Adds a {@link MetricRegistryListener} to a collection of listeners that will be notified on
     * metric creation.  Listeners will be notified in the order in which they are added.
     * <p/>
     * <b>N.B.:</b> The listener will be notified of all existing metrics when it first registers.
     *
     * @param listener the listener that will be notified
     */
    public void addListener(MetricRegistryListener listener) {
        metrics.addListener(listener);
    }

    /**
     * Removes a {@link MetricRegistryListener} from this registry's collection of listeners.
     *
     * @param listener the listener that will be removed
     */
    public void removeListener(MetricRegistryListener listener) {
        metrics.removeListener(listener);
    }

    /**
     * Returns a set of the names of all the metrics in the registry.
     *
     * @return the names of all the metrics
     */
    public SortedSet<String> getNames() {
        return metrics.getNames();
    }

    /**
     * Returns a map of all the gauges in the registry and their names.
     *
     * @return all the gauges in the registry
     */
    public SortedMap<String, Gauge> getGauges() {
        return metrics.getGauges();
    }

    /**
     * Returns a map of all the gauges in the registry and their names which match the given filter.
     *
     * @param filter    the metric filter to match
     * @return all the gauges in the registry
     */
    public SortedMap<String, Gauge> getGauges(MetricFilter filter) {
        return metrics.getGauges(filter);
    }

    /**
     * Returns a map of all the counters in the registry and their names.
     *
     * @return all the counters in the registry
     */
    public SortedMap<String, Counter> getCounters() {
        return metrics.getCounters();
    }

    /**
     * Returns a map of all the counters in the registry and their names.
     *
     * @return all the counters in the registry
     */
    public SortedMap<String, Counter> getCounters(MetricFilter filter) {
        return metrics.getCounters(filter);
    }

    /**
     * Returns a map of all the histograms in the registry and their names.
     *
     * @return all the histograms in the registry
     */
    public SortedMap<String, Histogram> getHistograms() {
        return metrics.getHistograms();
    }

    /**
     * Returns a map of all the histograms in the registry and their names.
     *
     * @return all the histograms in the registry
     */
    public SortedMap<String, Histogram> getHistograms(MetricFilter filter) {
        return metrics.getHistograms(filter);
    }

    /**
     * Returns a map of all the meters in the registry and their names.
     *
     * @return all the meters in the registry
     */
    public SortedMap<String, Meter> getMeters() {
        return metrics.getMeters();
    }

    /**
     * Returns a map of all the meters in the registry and their names.
     *
     * @return all the meters in the registry
     */
    public SortedMap<String, Meter> getMeters(MetricFilter filter) {
        return metrics.getMeters(filter);
    }

    /**
     * Returns a map of all the timers in the registry and their names.
     *
     * @return all the timers in the registry
     */
    public SortedMap<String, Timer> getTimers() {
        return metrics.getTimers();
    }

    /**
     * Returns a map of all the timers in the registry and their names.
     *
     * @return all the timers in the registry
     */
    public SortedMap<String, Timer> getTimers(MetricFilter filter) {
        return metrics.getTimers(filter);
    }

    /**
     * Registers an application {@link com.codahale.metrics.health.HealthCheck}.
     *
     * @param name        the name of the health check
     * @param healthCheck the {@link com.codahale.metrics.health.HealthCheck} instance
     */
    public static void register(String name, HealthCheck healthCheck) {
        healthMetrics.register(name, healthCheck);
    }

    /**
     * Unregisters the application {@link com.codahale.metrics.health.HealthCheck} with the given name.
     *
     * @param name the name of the {@link com.codahale.metrics.health.HealthCheck} instance
     */
    public static void unregister(String name) {
        healthMetrics.unregister(name);
    }

    /**
     * Returns a set of the names of all registered health checks.
     *
     * @return the names of all registered health checks
     */
    public SortedSet<String> getHealthNames() {
        return healthMetrics.getNames();
    }

    /**
     * Runs the health check with the given name.
     *
     * @param name    the health check's name
     * @return the result of the health check
     * @throws java.util.NoSuchElementException if there is no health check with the given name
     */
    public HealthCheck.Result runHealthCheck(String name) throws NoSuchElementException {
        return healthMetrics.runHealthCheck(name);
    }

    /**
     * Runs the registered health checks and returns a map of the results.
     *
     * @return a map of the health check results
     */
    public SortedMap<String, HealthCheck.Result> runHealthChecks() {
        return healthMetrics.runHealthChecks();
    }

    /**
     * Runs the registered health checks in parallel and returns a map of the results.
     *
     * @return a map of the health check results
     */
    public SortedMap<String, HealthCheck.Result> runHealthChecks(ExecutorService executor) {
        return healthMetrics.runHealthChecks(executor);
    }

}
