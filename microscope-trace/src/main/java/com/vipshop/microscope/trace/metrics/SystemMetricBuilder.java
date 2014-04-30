package com.vipshop.microscope.trace.metrics;

import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.storage.StorageHolder;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * Build System metric
 *
 * @author Xu Fei
 * @version 1.0
 */
public class SystemMetricBuilder {

    private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    public static void recordSystemMetric() {
        new SystemMetricBuilder().build();
    }

    private void build() {

        SystemMetric metric = SystemMetric.pid(getPID())
                .withApp(Tracer.APP_NAME)
                .withHost(IPAddressUtil.IPAddress())
                .withSystemProperties(runtimeMXBean.getSystemProperties())
                .withJVMArguments(runtimeMXBean.getInputArguments())
                .withOSVersion(osBean.getVersion())
                .withProcessorCount(osBean.getAvailableProcessors())
                .build();

        StorageHolder.getStorage().addSystemMetric(metric);

    }


    private Integer getPID() {
        String name = runtimeMXBean.getName();
        int index = name.indexOf("@");
        if (index != -1) {
            return Integer.parseInt(name.substring(0, index));
        }
        return 0;
    }

}
