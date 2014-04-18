package com.vipshop.microscope.trace.metrics.jvm;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Xu Fei
 * @version 1.0
 */
public class ClassMetricsSet implements MetricSet {

    private final ClassLoadingMXBean clBean = ManagementFactory.getClassLoadingMXBean();

    /**
     * A map of metric names to metrics.
     *
     * @return the metrics
     */
    @Override
    public Map<String, Metric> getMetrics() {
        final Map<String, Metric> gauges = new LinkedHashMap<String, Metric>();

        gauges.put("count.loaded", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return clBean.getTotalLoadedClassCount();
            }
        });

        gauges.put("count.unloaded", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return clBean.getUnloadedClassCount();
            }
        });

        gauges.put("count.shared-loaded", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return 0l;
            }
        });

        gauges.put("count.shared-unloaded", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return 0l;
            }
        });

        return gauges;
    }
}
