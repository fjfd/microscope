package com.vipshop.microscope.trace.metrics;

import com.codahale.metrics.MetricRegistry;

public class MetricsHolder {
	
	private static class MetricRegistryHolder {
		private static final MetricRegistry metrics = new MetricRegistry();
	}
	
	public static MetricRegistry getMetricRegistry() {
		return MetricRegistryHolder.metrics;
	}

}
