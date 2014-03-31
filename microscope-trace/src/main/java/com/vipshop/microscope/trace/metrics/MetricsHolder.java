package com.vipshop.microscope.trace.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * Metrics holder
 * 
 * @see MetricRegistry
 * @see HealthCheckRegistry
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsHolder {
	
	private static class MetricRegistryHolder {
		private static final MetricRegistry metrics = new MetricRegistry();
	}
	
	public static MetricRegistry getMetricRegistry() {
		return MetricRegistryHolder.metrics;
	}
	
	private static class HealthCheckRegistryHolder {
		private static final HealthCheckRegistry metrics = new HealthCheckRegistry();
	}
	
	public static HealthCheckRegistry getHealthCheckRegistry() {
		return HealthCheckRegistryHolder.metrics;
	}

}
