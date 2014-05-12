package com.vipshop.microscope.client.config;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.client.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A file reload config
 *
 * @author Xu Fei
 * @version 1.0
 */
public class FileReloadConfig extends AbstractConfig implements Config {

    private static final Logger logger = LoggerFactory.getLogger(FileReloadConfig.class);

    private static final int OPEN = 1;

    private static ScheduledExecutorService executor = ThreadPoolUtil.newSingleDaemonScheduledThreadPool("reload-trace.properties-thread");

    private static volatile int IS_TRACE_OPEN = 0;
    private static volatile int IS_METRIC_OPEN = 0;

    @Override
    public boolean hasConfigFile(String file) {
        return ConfigurationUtil.fileExist(file);
    }

    @Override
    public ConfigurationUtil initConfigFile(final String file) {

        logger.info("start file reload config thread, reload trace.properties file every 10 second");

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (hasConfigFile(file)) {
                    ConfigurationUtil config = ConfigurationUtil.getConfiguration(file);
                    IS_TRACE_OPEN = config.getInt("trace_switch");
                    IS_METRIC_OPEN = config.getInt("metric_switch");
                }
            }
        }, 0, 10, TimeUnit.SECONDS);

        return ConfigurationUtil.getConfiguration(file);
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
