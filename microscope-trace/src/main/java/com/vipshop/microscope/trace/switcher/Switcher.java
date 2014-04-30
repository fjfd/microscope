package com.vipshop.microscope.trace.switcher;

/**
 * A flag to turn trace/metric function on/off.
 *
 * @author Xu Fei
 * @version 1.0
 */
public interface Switcher {

    public boolean isTraceOpen();

    public boolean isMetricOpen();

    public void closeTrace();

    public void closeMetric();
}
