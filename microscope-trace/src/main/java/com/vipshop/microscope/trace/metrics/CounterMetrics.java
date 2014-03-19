package com.vipshop.microscope.trace.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

/**
 * Counter Metrics
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class CounterMetrics {
	
	private static final MetricRegistry metrics = MetricsHolder.getMetricRegistry();
	
	public static Counter getCounter(String name) {
		return metrics.counter(name);
	}
	
	public static Counter getCounter(Class<?> klass, String... names) {
		return metrics.counter(MetricRegistry.name(klass, names));
	}
	
}
