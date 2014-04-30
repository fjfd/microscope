package com.vipshop.microscope.trace.switcher;

import com.vipshop.microscope.trace.Tracer;

/**
 * A config switcher
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ConfigSwitcher implements Switcher {

    private static final int TRACE_OPEN = 1;
    private static final int METRIC_OPEN = 1;

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
