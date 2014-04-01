package com.vipshop.microscope.trace.metrics.jvm;

import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

public class IOMetricsSet implements MetricSet {

	@Override
	public Map<String, Metric> getMetrics() {
		final Map<String, Metric> gauges = new HashMap<String, Metric>();
		return gauges;
	}

}
