package com.vipshop.microscope.trace.metrics.jvm;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

public class DiskMetricsSet implements MetricSet {
	File[] roots = File.listRoots();

	final Map<String, Metric> gauges = new HashMap<String, Metric>();

	@Override
	public Map<String, Metric> getMetrics() {
		gauges.put("total.space", new Gauge<Long>() {
			@Override
			public Long getValue() {
				long space = 0l;
				for (File root : roots) {
					space += root.getTotalSpace();
				}
				return space;
			}
		});

		gauges.put("free.space", new Gauge<Long>() {
			@Override
			public Long getValue() {
				long space = 0l;
				for (File root : roots) {
					space += root.getFreeSpace();
				}
				return space;
			}
		});

		gauges.put("usable.space", new Gauge<Long>() {
			@Override
			public Long getValue() {
				long space = 0l;
				for (File root : roots) {
					space += root.getUsableSpace();
				}
				return space;
			}
		});

		return gauges;
	}

}