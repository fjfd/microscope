package com.vipshop.microscope.client.configurator;

import com.vipshop.microscope.common.util.ConfigurationUtil;

public abstract class AbstractConfigurator {

    protected volatile ConfigurationUtil configurationUtil;

    protected final int TRACE_OPEN = 1;
    protected final int METRIC_OPEN = 1;
    protected final int SYSTEM_OPEN = 1;
    protected final int EXCEPTION_OPEN = 1;
    protected final int TRANSPORT_OPEN = 1;

}
