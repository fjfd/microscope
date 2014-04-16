/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vipshop.microscope.trace.metrics.reporter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Clock;
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
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.trace.metrics.Metrics;
import com.vipshop.microscope.trace.stoarge.Storage;
import com.vipshop.microscope.trace.stoarge.StorageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A reporter which publishes metric values to a Microscope server.
 *
 * @author Sean Scanlon <sean.scanlon@gmail.com>
 */
public class MicroscopeReporter extends ScheduledReporter {

    private static final Logger logger = LoggerFactory.getLogger(MicroscopeReporter.class);

    private final Clock clock;

	private final Storage output;
	
	private final Map<String, String> tags;

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
     * A builder for {@link MicroscopeReporter} instances. Defaults to not using a prefix, using the
     * default clock, converting rates to events/second, converting durations to milliseconds, and
     * not filtering metrics.
     */
    public static class Builder {
        private final MetricRegistry registry;
        
        private final Storage output;

        private Clock clock;

        private TimeUnit rateUnit;

        private TimeUnit durationUnit;

        private MetricFilter filter;
        
        private Map<String, String> tags;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.output = StorageHolder.getStorage();
            this.clock = Clock.defaultClock();
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.filter = MetricFilter.ALL;
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
         * Append tags to all reported metrics
         *
         * @param tags
         * @return
         */
        public Builder withTags(Map<String, String> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * Builds a {@link MicroscopeReporter} with the given properties, sending metrics using the
         * given {@link com.vipshop.microscope.trace.metrics} client.
         *
         * @return a {@link MicroscopeReporter}
         */
        public MicroscopeReporter build() {
            return new MicroscopeReporter(registry,
					                    output,
					                    clock,
					                    rateUnit,
					                    durationUnit,
					                    filter, 
					                    tags);
        }
    }
    
    private MicroscopeReporter(MetricRegistry registry, 
    						   Storage output,
    						   Clock clock,
    						   TimeUnit rateUnit,
    						   TimeUnit durationUnit,
    						   MetricFilter filter,
    						   Map<String, String> tags) {
        super(registry, "microscope-reporter", filter, rateUnit, durationUnit);
        this.output = output;
        this.clock = clock;
        this.tags = tags;
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges, 
    				   SortedMap<String, Counter> counters, 
    				   SortedMap<String, Histogram> histograms, 
    				   SortedMap<String, Meter> meters, 
    				   SortedMap<String, Timer> timers) {

        final long timestamp = clock.getTime();

        for (Map.Entry<String, Gauge> g : gauges.entrySet()) {
        	buildGauge(g.getKey(), g.getValue(), timestamp);
        }

        for (Map.Entry<String, Counter> entry : counters.entrySet()) {
        	buildCounter(entry.getKey(), entry.getValue(), timestamp);
        }

        for (Map.Entry<String, Histogram> entry : histograms.entrySet()) {
        	buildHistograms(entry.getKey(), entry.getValue(), timestamp);
        }
        
        for (Map.Entry<String, Meter> entry : meters.entrySet()) {
        	buildMeters(entry.getKey(), entry.getValue(), timestamp);
        }

        for (Map.Entry<String, Timer> entry : timers.entrySet()) {
        	buildTimers(entry.getKey(), entry.getValue(), timestamp);
        }
        
        Map<String, HealthCheck.Result> results = Metrics.runHealthChecks();
        if (!results.isEmpty()) {
        	for (Entry<String, HealthCheck.Result> entry : results.entrySet()){
        		buildHealths(entry.getKey(), entry.getValue(), timestamp);
        	}
        }

    }
    
    private void buildGauge(String name, Gauge gauge, long timestamp) {

        logger.debug("add gauge metrics to storage queue");

    	output.addMetrics(Metric.named(name)
    			                .withValue(gauge.getValue())
    			                .withTimestamp(timestamp)
    			                .withTags(tags)
    			                .build());
    }

    private void buildCounter(String name, Counter counter, long timestamp) {

        logger.debug("add counter metrics to storage queue");

        output.addMetrics(Metric.named(name)
                	            .withTimestamp(timestamp)
                	            .withValue(counter.getCount())
                	            .withTags(tags)
                	            .build());
    }
    
    private void buildHistograms(String name, Histogram histogram, long timestamp) {

        logger.debug("add histogram metrics to storage queue");

        final Snapshot snapshot = histogram.getSnapshot();
        output.addMetrics(Metric.named(prefix(name, "count"))
                                .withTimestamp(timestamp)
                                .withValue(histogram.getCount())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "max"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getMax())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "min"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getMin())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "mean"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getMean())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "stddev"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getStdDev())
                                .withTags(tags).build());


        output.addMetrics(Metric.named(prefix(name, "p50"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getMedian())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "p75"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get75thPercentile())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "p95"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get95thPercentile())
                                .withTags(tags).build());


        output.addMetrics(Metric.named(prefix(name, "p98"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get98thPercentile())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "p99"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get99thPercentile())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "p999"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get999thPercentile())
                                .withTags(tags).build());

    }

    private void buildMeters(String name, Meter meter, long timestamp) {

        logger.debug("add meter metrics to storage queue");

    	output.addMetrics(Metric.named(prefix(name, "count"))
                                .withTimestamp(timestamp)
                                .withValue(meter.getCount())
                                .withTags(tags).build());
    	
    	output.addMetrics(Metric.named(prefix(name, "meanrate"))
                                .withTimestamp(timestamp)
                                .withValue(meter.getMeanRate())
                                .withTags(tags).build());

    	output.addMetrics(Metric.named(prefix(name, "1meanrate"))
                                .withTimestamp(timestamp)
                                .withValue(meter.getOneMinuteRate())
                                .withTags(tags).build());

    	output.addMetrics(Metric.named(prefix(name, "5meanrate"))
                                .withTimestamp(timestamp)
                                .withValue(meter.getFiveMinuteRate())
                                .withTags(tags).build());

    	output.addMetrics(Metric.named(prefix(name, "15meanrate"))
                                .withTimestamp(timestamp)
                                .withValue(meter.getFifteenMinuteRate())
                                .withTags(tags).build());

    }

    private void buildTimers(String name, Timer timer, long timestamp) {

        logger.debug("add timer metrics to storage queue");

        final Snapshot snapshot = timer.getSnapshot();

        output.addMetrics(Metric.named(prefix(name, "count"))
                                .withTimestamp(timestamp)
                                .withValue(timer.getCount())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "max"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getMax())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "min"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getMin())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "mean"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getMean())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "stddev"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getStdDev())
                                .withTags(tags).build());


        output.addMetrics(Metric.named(prefix(name, "p50"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.getMedian())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "p75"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get75thPercentile())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "p95"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get95thPercentile())
                                .withTags(tags).build());


        output.addMetrics(Metric.named(prefix(name, "p98"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get98thPercentile())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "p99"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get99thPercentile())
                                .withTags(tags).build());

        output.addMetrics(Metric.named(prefix(name, "p999"))
                                .withTimestamp(timestamp)
                                .withValue(snapshot.get999thPercentile())
                                .withTags(tags).build());

    }

    private void buildHealths(String name, HealthCheck.Result result, long timestamp) {

        logger.debug("add health metrics to storage queue");

    	output.addMetrics(Metric.named(prefix(name, "health"))
                                .withTimestamp(timestamp)
                                .withValue(result.isHealthy())
                                .withTags(tags)
                                .build());
    }

    private String prefix(String... names) {
    	final StringBuilder builder = new StringBuilder();
        if (names != null) {
            for (String s : names) {
                builder.append(s).append(".");
            }
        }
        String name = builder.toString();
        return name.substring(0, name.length() - 1);
    }

}
