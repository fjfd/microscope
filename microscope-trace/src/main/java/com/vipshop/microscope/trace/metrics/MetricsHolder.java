package com.vipshop.microscope.trace.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * Metrics holder
 *
 * @author Xu Fei
 * @version 1.0
 * @see MetricRegistry
 * @see HealthCheckRegistry
 */
public class MetricsHolder {

    public static MetricRegistry getMetricRegistry() {
        return MetricRegistryHolder.metrics;
    }

    public static HealthCheckRegistry getHealthCheckRegistry() {
        return HealthCheckRegistryHolder.metrics;
    }

    private static class MetricRegistryHolder {
        private static final MetricRegistry metrics = new MetricRegistry();
    }

    private static class HealthCheckRegistryHolder {
        private static final HealthCheckRegistry metrics = new HealthCheckRegistry();
    }

}
