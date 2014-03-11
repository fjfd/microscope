package com.vipshop.microscope.trace.metrics;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
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
import com.codahale.metrics.Timer;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.stoarge.QueueStorage;
import com.vipshop.microscope.trace.stoarge.Storage;

/**
 * A reporter which outputs measurements to {@code Microscope Collector}.
 */
@SuppressWarnings("unused")
public class MetricsReporter extends ScheduledReporter {
    
	/**
     * Returns a new {@link Builder} for {@link MetricsReporter}.
     *
     * @param registry the registry to report
     * @return a {@link Builder} instance for a {@link MetricsReporter}
     */
    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    /**
     * A builder for {@link MetricsReporter} instances. Defaults to using the default locale and
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
            this.output = QueueStorage.getStorage();
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
        public Builder outputTo(QueueStorage output) {
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
        public MetricsReporter build() {
            return new MetricsReporter(registry,
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

    private MetricsReporter(MetricRegistry registry,
    		                Storage output,
                            Locale locale,
                            Clock clock,
                            TimeZone timeZone,
                            TimeUnit rateUnit,
                            TimeUnit durationUnit,
                            MetricFilter filter) {
        super(registry, "metrics-reporter", filter, rateUnit, durationUnit);
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

        if (!gauges.isEmpty()) {
        	output.addGauge(gauges, dateTime);
        }

        if (!counters.isEmpty()) {
        	output.addCounter(counters, dateTime);
        }

        if (!histograms.isEmpty()) {
        	output.addHistogram(histograms, dateTime);
        }

        if (!meters.isEmpty()) {
        	output.addMeter(meters, dateTime);
        }

        if (!timers.isEmpty()) {
        	output.addTimer(timers, dateTime);
        }

    }

}
