package com.vipshop.microscope.trace.metrics;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Clock;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheck;
import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.stoarge.ArrayBlockingQueueStorage;
import com.vipshop.microscope.trace.stoarge.Storage;
import com.vipshop.microscope.trace.stoarge.StorageHolder;

/**
 * A reporter which outputs measurements to {@code Microscope Collector}.
 */
@SuppressWarnings("unused")
public class MicroscopeReporter extends ScheduledReporter {
    
	/**
     * Returns a new {@link Builder} for {@link MicroscopeReporter}.
     *
     * @param registry the registry to report
     * @return a {@link Builder} instance for a {@link MicroscopeReporter}
     */
    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    /**
     * A builder for {@link MicroscopeReporter} instances. Defaults to using the default locale and
     * time zone, writing to {@code Microscope collector}, converting rates to events/second, converting
     * durations to milliseconds, and not filtering metrics.
     */
    public static class Builder {
        private final MetricRegistry registry;
        private Storage output;
        private Locale locale;
        private Clock clock;
        private TimeZone timeZone;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private MetricFilter filter;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.output = StorageHolder.getStorage();
            this.locale = Locale.getDefault();
            this.clock = Clock.defaultClock();
            this.timeZone = TimeZone.getDefault();
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.filter = MetricFilter.ALL;
        }

        /**
         * Write to the given {@link PrintStream}.
         *
         * @param output a {@link PrintStream} instance.
         * @return {@code this}
         */
        public Builder outputTo(ArrayBlockingQueueStorage output) {
            this.output = output;
            return this;
        }

        /**
         * Format numbers for the given {@link Locale}.
         *
         * @param locale a {@link Locale}
         * @return {@code this}
         */
        public Builder formattedFor(Locale locale) {
            this.locale = locale;
            return this;
        }

        /**
         * Use the given {@link Clock} instance for the time.
         *
         * @param clock a {@link Clock} instance
         * @return {@code this}
         */
        public Builder withClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        /**
         * Use the given {@link TimeZone} for the time.
         *
         * @param timeZone a {@link TimeZone}
         * @return {@code this}
         */
        public Builder formattedFor(TimeZone timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        /**
         * Convert rates to the given time unit.
         *
         * @param rateUnit a unit of time
         * @return {@code this}
         */
        public Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        /**
         * Convert durations to the given time unit.
         *
         * @param durationUnit a unit of time
         * @return {@code this}
         */
        public Builder convertDurationsTo(TimeUnit durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        /**
         * Only report metrics which match the given filter.
         *
         * @param filter a {@link MetricFilter}
         * @return {@code this}
         */
        public Builder filter(MetricFilter filter) {
            this.filter = filter;
            return this;
        }

        /**
         * Builds a {@link ConsoleReporter} with the given properties.
         *
         * @return a {@link ConsoleReporter}
         */
        public MicroscopeReporter build() {
            return new MicroscopeReporter(registry,
                                       output,
                                       locale,
                                       clock,
                                       timeZone,
                                       rateUnit,
                                       durationUnit,
                                       filter);
        }
    }

	private static final int CONSOLE_WIDTH = 80;

    private final Storage output;
    private final Locale locale;
    private final Clock clock;
    private final DateFormat dateFormat;

    private MicroscopeReporter(MetricRegistry registry,
    		                Storage output,
                            Locale locale,
                            Clock clock,
                            TimeZone timeZone,
                            TimeUnit rateUnit,
                            TimeUnit durationUnit,
                            MetricFilter filter) {
        super(registry, "microscope-reporter", filter, rateUnit, durationUnit);
        this.output = output;
        this.locale = locale;
        this.clock = clock;
        this.dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                                                         DateFormat.MEDIUM,
                                                         locale);
        dateFormat.setTimeZone(timeZone);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void report(SortedMap<String, Gauge> gauges,
                       SortedMap<String, Counter> counters,
                       SortedMap<String, Histogram> histograms,
                       SortedMap<String, Meter> meters,
                       SortedMap<String, Timer> timers) {
        final long dateTime = TimeStampUtil.currentTimeMillis();
        
        if (!counters.isEmpty()) {
        	HashMap<String, Object> metrics = new HashMap<String, Object>();
    		metrics.put("type", MetricsCategory.Counter);
    		metrics.put("date", dateTime);
    		metrics.put("app", Tracer.APP_NAME);
    		metrics.put("ip", IPAddressUtil.IPAddress());

    		for (Entry<String, Counter> entry : counters.entrySet()) {
    			metrics.put(entry.getKey(), entry.getValue().getCount());
    		}
    		output.addMetrics(metrics);
        }

        if (!gauges.isEmpty()) {
        	HashMap<String, Object> metrics = new LinkedHashMap<String, Object>();
    		
    		metrics.put("type", MetricsCategory.Gauge);
    		metrics.put("date", dateTime);
    		metrics.put("app", Tracer.APP_NAME);
    		metrics.put("ip", IPAddressUtil.IPAddress());
    		
    		for (Entry<String, Gauge> entry : gauges.entrySet()) {
    			String key = entry.getKey();
    			if (key.contains(".")) {
					key = key.replace(".", "_");
				}
    			metrics.put(key, entry.getValue().getValue());
    		}
        	output.addMetrics(metrics);
        }

        if (!histograms.isEmpty()) {
        	HashMap<String, Object> metrics = new HashMap<String, Object>();
    		metrics.put("type", MetricsCategory.Histogram);
    		metrics.put("date", dateTime);
    		metrics.put("app", Tracer.APP_NAME);
    		metrics.put("ip", IPAddressUtil.IPAddress());

    		for (Map.Entry<String, Histogram> entry : histograms.entrySet()) {
    			Histogram histogram = entry.getValue();
    			Snapshot snapshot = histogram.getSnapshot();
    			HashMap<String, Object> values = new HashMap<String, Object>();
    			values.put("count", histogram.getCount());
    			values.put("min", snapshot.getMin());
    			values.put("max", snapshot.getMax());
    			values.put("mean", snapshot.getMean());
    			values.put("stddev", snapshot.getStdDev());
    			values.put("median", snapshot.getMedian());
    			values.put("75%", snapshot.get75thPercentile());
    			values.put("95%", snapshot.get95thPercentile());
    			values.put("98%", snapshot.get98thPercentile());
    			values.put("99%", snapshot.get99thPercentile());
    			values.put("99.9%", snapshot.get999thPercentile());
    			metrics.put(entry.getKey(), values);
    		}
    		output.addMetrics(metrics);
        }

        if (!meters.isEmpty()) {
        	HashMap<String, Object> metrics = new HashMap<String, Object>();
        	metrics.put("type", MetricsCategory.Meter);
    		metrics.put("date", dateTime);
    		metrics.put("app", Tracer.APP_NAME);
    		metrics.put("ip", IPAddressUtil.IPAddress());
        	for (Map.Entry<String, Meter> entry : meters.entrySet()) {
    			Meter meter = entry.getValue();
    			HashMap<String, Object> values = new HashMap<String, Object>();
    			values.put("count", meter.getCount());
    			values.put("mean rate", convertRate(meter.getMeanRate()));
    			values.put("1-minute rate", convertRate(meter.getOneMinuteRate()));
    			values.put("5-minute rate", convertRate(meter.getFiveMinuteRate()));
    			values.put("15-minute rate", convertRate(meter.getFifteenMinuteRate()));
    			metrics.put(entry.getKey(), values);
    		}
        	output.addMetrics(metrics);
        }

        if (!timers.isEmpty()) {
        	HashMap<String, Object> metrics = new HashMap<String, Object>();
        	metrics.put("type", MetricsCategory.Timer);
    		metrics.put("date", dateTime);
    		metrics.put("app", Tracer.APP_NAME);
    		metrics.put("ip", IPAddressUtil.IPAddress());
    		for (Map.Entry<String, Timer> entry : timers.entrySet()) {
    			Timer timer = entry.getValue();
    			Snapshot snapshot = timer.getSnapshot();
    			HashMap<String, Object> values = new HashMap<String, Object>();
    			values.put("count", timer.getCount());
    			values.put("mean rate", convertRate(timer.getMeanRate()));
    			values.put("1-minute rate", convertRate(timer.getOneMinuteRate()));
    			values.put("5-minute rate", convertRate(timer.getFiveMinuteRate()));
    			values.put("15-minute rate", convertRate(timer.getFifteenMinuteRate()));
    			values.put("min", snapshot.getMin());
    			values.put("max", snapshot.getMax());
    			values.put("mean", snapshot.getMean());
    			values.put("stddev", snapshot.getStdDev());
    			values.put("median", snapshot.getMedian());
    			values.put("75%", snapshot.get75thPercentile());
    			values.put("95%", snapshot.get95thPercentile());
    			values.put("98%", snapshot.get98thPercentile());
    			values.put("99%", snapshot.get99thPercentile());
    			values.put("99.9%", snapshot.get999thPercentile());
    			metrics.put(entry.getKey(), values);
    		}
    		output.addMetrics(metrics);
        }
        
    }

}
