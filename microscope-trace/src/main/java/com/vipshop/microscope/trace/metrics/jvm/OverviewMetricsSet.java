package com.vipshop.microscope.trace.metrics.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.vipshop.microscope.common.util.IPAddressUtil;

public class OverviewMetricsSet implements MetricSet {
	
	private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
	private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    /**
     * Record system metrics only once.
     */
    private volatile boolean isRecord = false;

	@Override
	public Map<String, Metric> getMetrics() {

        final Map<String, Metric> gauges = new LinkedHashMap<String, Metric>();

        if (!isRecord) {

            final Map<String, String> system = runtimeMXBean.getSystemProperties();

            // **************************** process *************************************//
            gauges.put("PID", new Gauge<Integer>() {
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

            gauges.put("Host", new Gauge<String>() {
                @Override
                public String getValue() {
                    return IPAddressUtil.IPAddress();
                }
            });

            gauges.put("MainClass", new Gauge<String>() {
                @Override
                public String getValue() {
                    return system.get("sun.java.command");
                }
            });

            gauges.put("Arguments", new Gauge<String>() {
                @Override
                public String getValue() {
                    return "none";
                }
            });


            // *********************** JVM ************************** //
            gauges.put("JVM", new Gauge<String>() {
                @Override
                public String getValue() {
                    return system.get("java.vm.name") + "(" + system.get("java.vm.version") + ", " + system.get("java.vm.info") + ")";
                }
            });

            gauges.put("Java", new Gauge<String>() {
                @Override
                public String getValue() {
                    return "version " + system.get("java.version") + ", vendor " + system.get("java.vendor");
                }
            });

            gauges.put("JavaHome", new Gauge<String>() {
                @Override
                public String getValue() {
                    return system.get("java.home");
                }
            });

            gauges.put("JVMFlags", new Gauge<String>() {
                @Override
                public String getValue() {
                    return "none";
                }
            });

            gauges.put("JVMArguments", new Gauge<List<String>>() {

                @Override
                public List<String> getValue() {
                    return runtimeMXBean.getInputArguments();
                }
            });

            // ************************ OS ***************************** //
            gauges.put("OSVersion", new Gauge<String>() {
                @Override
                public String getValue() {
                    return osBean.getVersion();
                }
            });

            gauges.put("OSArch", new Gauge<String>() {
                @Override
                public String getValue() {
                    return system.get("os.arch");
                }
            });

            gauges.put("OSName", new Gauge<String>() {
                @Override
                public String getValue() {
                    return system.get("os.name");
                }
            });

            gauges.put("AvailableProcessors", new Gauge<Integer>() {
                @Override
                public Integer getValue() {
                    return osBean.getAvailableProcessors();
                }
            });

            isRecord = true;
        }

		return gauges;
	}
	
	
}