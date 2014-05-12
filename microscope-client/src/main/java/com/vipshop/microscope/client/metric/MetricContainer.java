package com.vipshop.microscope.client.metric;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * Metrics container
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MetricContainer {

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
