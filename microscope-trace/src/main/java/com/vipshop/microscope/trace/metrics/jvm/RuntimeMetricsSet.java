package com.vipshop.microscope.trace.metrics.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

public class RuntimeMetricsSet implements MetricSet {

	RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

	@Override
	public Map<String, Metric> getMetrics() {
		final Map<String, Metric> gauges = new HashMap<String, Metric>();

		gauges.put("start.time", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return runtimeMXBean.getStartTime();
			}
		});

		gauges.put("up.time", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return runtimeMXBean.getUptime();
			}
		});

		gauges.put("java.classpath", new Gauge<String>() {
			@Override
			public String getValue() {
				return runtimeMXBean.getClassPath();
			}
		});

		gauges.put("process.name", new Gauge<String>() {
			@Override
			public String getValue() {
				return runtimeMXBean.getName();
			}
		});

		gauges.put("process.id", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				String name = runtimeMXBean.getName();
				int index = name.indexOf("@");
				if (index != -1) {
					return Integer.parseInt(name.substring(0, index));
				}
				return 0;
			}
		});

		gauges.put("system.properties", new Gauge<Map<String, String>>() {
			@Override
			public Map<String, String> getValue() {
				return runtimeMXBean.getSystemProperties();
			}
		});

		return gauges;
	}
}