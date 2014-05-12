package com.vipshop.microscope.client.config;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.client.Tracer;

/**
 * A config switcher
 *
 * @author Xu Fei
 * @version 1.0
 */
public class FileConfig extends AbstractConfig implements Config {

    private static final int TRACE_OPEN = 1;
    private static final int METRIC_OPEN = 1;

    @Override
    public boolean hasConfigFile(String file) {
        return ConfigurationUtil.fileExist(file);
    }

    @Override
    public ConfigurationUtil initConfigFile(String file) {
        return ConfigurationUtil.getConfiguration(file);
    }

    @Override
    public boolean isTraceOpen() {
        return Tracer.TRACE_SWITCH == TRACE_OPEN;
    }

    @Override
    public boolean isMetricOpen() {
        return Tracer.METRIC_SWITCH == METRIC_OPEN;
    }

    @Override
    public void closeTrace() {
        Tracer.TRACE_SWITCH = 0;
    }

    @Override
    public void closeMetric() {
        Tracer.METRIC_SWITCH = 0;
    }

}
