package com.vipshop.microscope.trace.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Monitor metrics
 * <p/>
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
    private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

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

    @Override
    public Map<String, Metric> getMetrics() {
        final Map<String, Metric> gauges = new LinkedHashMap<String, Metric>();

        gauges.put("time.jvm-start", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return runtimeMXBean.getStartTime();
            }
        });

        gauges.put("time.jvm-up", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return runtimeMXBean.getUptime();
            }
        });

        // ***************************** monitor CPU ********************************* //
        gauges.put("system.load-average", new Gauge<Double>() {
            @Override
            public Double getValue() {
                return osBean.getSystemLoadAverage();
            }
        });

        // ***************************** monitor memory ****************************** //
        if (isInstanceOfInterface(osBean.getClass(), "com.sun.management.OperatingSystemMXBean")) {
            final com.sun.management.OperatingSystemMXBean b = (com.sun.management.OperatingSystemMXBean) osBean;

            gauges.put("memory.total-physical", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getTotalPhysicalMemorySize();
                }
            });

            gauges.put("memory.free-physical", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getFreePhysicalMemorySize();
                }
            });

            gauges.put("memory.committed-virtual", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getCommittedVirtualMemorySize();
                }
            });

            // ***************************** monitor disk   ****************************** //
            gauges.put("space.total-swap", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getTotalSwapSpaceSize();
                }
            });

            gauges.put("space.free-swap", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getFreeSwapSpaceSize();
                }
            });

            gauges.put("system.cpu-Time", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getProcessCpuTime();
                }
            });
        }

        return gauges;
    }
}
