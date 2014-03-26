package com.vipshop.microscope.trace.metrics.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

public class OSMetricsSet implements MetricSet {
	OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();

	@SuppressWarnings("restriction")
	@Override
	public Map<String, Metric> getMetrics() {
		final Map<String, Metric> gauges = new HashMap<String, Metric>();

		gauges.put("arch", new Gauge<String>() {
			@Override
			public String getValue() {
				return bean.getArch();
			}
		});

		gauges.put("name", new Gauge<String>() {
			@Override
			public String getValue() {
				return bean.getName();
			}
		});

		gauges.put("version", new Gauge<String>() {
			@Override
			public String getValue() {
				return bean.getVersion();
			}
		});

		gauges.put("availableprocessors", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return bean.getAvailableProcessors();
			}
		});

		gauges.put("systemloadaverage", new Gauge<Double>() {
			@Override
			public Double getValue() {
				return bean.getSystemLoadAverage();
			}
		});

		if (isInstanceOfInterface(bean.getClass(), "com.sun.management.OperatingSystemMXBean")) {
			final com.sun.management.OperatingSystemMXBean b = (com.sun.management.OperatingSystemMXBean) bean;

			gauges.put("TotalPhysicalMemory", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return b.getTotalPhysicalMemorySize();
				}
			});

			gauges.put("FreePhysicalMemory", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return b.getFreePhysicalMemorySize();
				}
			});

			gauges.put("TotalSwapSpace", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return b.getTotalSwapSpaceSize();
				}
			});

			gauges.put("FreeSwapSpace", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return b.getFreeSwapSpaceSize();
				}
			});

			gauges.put("ProcessCpuTime", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return b.getProcessCpuTime();
				}
			});
			gauges.put("CommittedVirtualMemory", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return b.getCommittedVirtualMemorySize();
				}
			});

		}

		return gauges;
	}
	
	private static boolean isInstanceOfInterface(Class<?> clazz, String interfaceName) {
		if (clazz == Object.class) {
			return false;
		} else if (clazz.getName().equals(interfaceName)) {
			return true;
		}

		Class<?>[] interfaceclasses = clazz.getInterfaces();

		for (Class<?> interfaceClass : interfaceclasses) {
			if (isInstanceOfInterface(interfaceClass, interfaceName)) {
				return true;
			}
		}

		return isInstanceOfInterface(clazz.getSuperclass(), interfaceName);
	}
}