package com.vipshop.microscope.client.system;

import com.vipshop.microscope.client.Tracer;
import com.vipshop.microscope.common.util.IPAddressUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * System data builder
 *
 * @author Xu Fei
 * @version 1.0
 */
public class SystemDataBuilder {

    private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    public SystemData build() {

        SystemData data = SystemData.pid(getPID())
                .withApp(Tracer.APP_NAME)
                .withHost(IPAddressUtil.IPAddress())
                .withSystemProperties(runtimeMXBean.getSystemProperties())
                .withJVMArguments(runtimeMXBean.getInputArguments())
                .withOSVersion(osBean.getVersion())
                .withProcessorCount(osBean.getAvailableProcessors())
                .build();

        return data;

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
