package com.vipshop.microscope.trace.metrics;

import com.codahale.metrics.MetricRegistry;

/**
 * Store metrics in {@link MetricRegistry}.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsContainer {
	
	private static class MetricRegistryHolder {
		private static final MetricRegistry metrics = new MetricRegistry();
	}
	
	public static MetricRegistry getMetricRegistry() {
		return MetricRegistryHolder.metrics;
	}

}
