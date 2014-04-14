package com.vipshop.microscope.trace.metrics.system;

import com.vipshop.microscope.common.system.SystemInfo;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.stoarge.StorageHolder;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * Build System info
 *
 * @author Xu Fei
 * @version 1.0
 */
public class SystemInfoBuilder {

    private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    public static void recordSystemInfo() {
        new SystemInfoBuilder().build();
    }

    private void build() {

        SystemInfo info = SystemInfo.pid(getPID())
                                    .withApp(Tracer.APP_NAME)
                                    .withHost(IPAddressUtil.IPAddress())
                                    .withSystemProperties(runtimeMXBean.getSystemProperties())
                                    .withJVMArguments(runtimeMXBean.getInputArguments())
                                    .withOSVersion(osBean.getVersion())
                                    .withProcessorCount(osBean.getAvailableProcessors())
                                    .build();

        StorageHolder.getStorage().addSystemInfo(info);

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
