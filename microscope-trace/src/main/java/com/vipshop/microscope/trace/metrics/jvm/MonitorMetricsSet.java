package com.vipshop.microscope.trace.metrics.jvm;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.LinkedHashMap;
import java.util.Map;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

/**
 * Monitor metrics
 * 
 * CPU
 * Memeory
 * Disk
 * IO
 * Classes
 * Thread
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MonitorMetricsSet implements MetricSet {

	private final ClassLoadingMXBean clBean = ManagementFactory.getClassLoadingMXBean();
	private final ThreadMXBean threads = ManagementFactory.getThreadMXBean();
	private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
	private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	
	@SuppressWarnings("restriction")
	@Override
	public Map<String, Metric> getMetrics() {
		final Map<String, Metric> gauges = new LinkedHashMap<String, Metric>();
		
		gauges.put("JVMStartTime", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return runtimeMXBean.getStartTime();
			}
		});

		gauges.put("JVMUpTime", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return runtimeMXBean.getUptime();
			}
		});

		// ***************************** monitor CPU ********************************* //
		gauges.put("SystemLoadAverage", new Gauge<Double>() {
			@Override
			public Double getValue() {
				return osBean.getSystemLoadAverage();
			}
		});
		
		// ***************************** monitor memory ****************************** //
		if (isInstanceOfInterface(osBean.getClass(), "com.sun.management.OperatingSystemMXBean")) {
			final com.sun.management.OperatingSystemMXBean b = (com.sun.management.OperatingSystemMXBean) osBean;

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

			gauges.put("CommittedVirtualMemory", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return b.getCommittedVirtualMemorySize();
				}
			});

			// ***************************** monitor disk   ****************************** //
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
		}
		
		// ******************* monitor classes **************************** // 
		gauges.put("TotalLoadedClasses", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return clBean.getTotalLoadedClassCount();
			}
		});
		
		gauges.put("TotalUnloadedClasses", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return clBean.getUnloadedClassCount();
			}
		});
		
		gauges.put("SharedLoadedClasses", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return 0l;
			}
		});
		
		gauges.put("SharedUnloadedClasses", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return 0l;
			}
		});
		
		// *************************** monitor thread ************************ //
		gauges.put("Count", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return threads.getThreadCount();
			}
		});

		gauges.put("DaemonCount", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return threads.getDaemonThreadCount();
			}
		});
		
		gauges.put("PeakCount", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return threads.getPeakThreadCount();
			}
		});
		
		gauges.put("TotalStartCount", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return threads.getTotalStartedThreadCount();
			}
		});

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
