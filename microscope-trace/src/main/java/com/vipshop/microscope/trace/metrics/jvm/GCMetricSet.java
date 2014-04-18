package com.vipshop.microscope.trace.metrics.jvm;
import static com.codahale.metrics.MetricRegistry.name;

import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

/**
 * A set of gauges for the counts and elapsed times of garbage collections.
 */
public class GCMetricSet implements MetricSet {
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");

    private final List<GarbageCollectorMXBean> garbageCollectors;
    
    private final CompilationMXBean cBean = ManagementFactory.getCompilationMXBean();

    /**
     * Creates a new set of gauges for all discoverable garbage collectors.
     */
    public GCMetricSet() {
        this(ManagementFactory.getGarbageCollectorMXBeans());
    }

    /**
     * Creates a new set of gauges for the given collection of garbage collectors.
     *
     * @param garbageCollectors    the garbage collectors
     */
    public GCMetricSet(Collection<GarbageCollectorMXBean> garbageCollectors) {
        this.garbageCollectors = new ArrayList<GarbageCollectorMXBean>(garbageCollectors);
    }

    @Override
    public Map<String, Metric> getMetrics() {
        final Map<String, Metric> gauges = new LinkedHashMap<String, Metric>();
        for (final GarbageCollectorMXBean gc : garbageCollectors) {
            final String name = WHITESPACE.matcher(gc.getName()).replaceAll("-");
            gauges.put(name(name, "count"), new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return gc.getCollectionCount();
                }
            });

            gauges.put(name(name, "time"), new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return gc.getCollectionTime();
                }
            });
        }
        
        gauges.put(("total-compilation.time"), new Gauge<Long>() {
            @Override
            public Long getValue() {
                return cBean.getTotalCompilationTime();
            }
        });
        
        return Collections.unmodifiableMap(gauges);
    }
}