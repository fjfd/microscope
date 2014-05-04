package com.vipshop.microscope.trace.switcher;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A config reload switcher
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ConfigReloadSwitcher implements Switcher {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReloadSwitcher.class);

    private static final int OPEN = 1;

    private static ScheduledExecutorService executor = ThreadPoolUtil.newSingleDaemonScheduledThreadPool("reload-trace.properties-thread");

    private static volatile int IS_TRACE_OPEN = 0;
    private static volatile int IS_METRIC_OPEN = 0;

    static {

        logger.info("start config reload switcher thread, reload trace.properties file every 10 second");

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (ConfigurationUtil.fileExist("trace.properties")) {
                    ConfigurationUtil config = ConfigurationUtil.getConfiguration("trace.properties");
                    IS_TRACE_OPEN = config.getInt("trace_switch");
                    IS_METRIC_OPEN = config.getInt("metric_switch");
                }
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public boolean isTraceOpen() {
        return IS_TRACE_OPEN == OPEN;
    }

    @Override
    public boolean isMetricOpen() {
        return IS_METRIC_OPEN == OPEN;
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
