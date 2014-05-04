package com.vipshop.microscope.trace.switcher;

import com.vipshop.microscope.common.util.ThreadPoolUtil;
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
public class DBReloadSwitcher implements Switcher {

    private static final Logger logger = LoggerFactory.getLogger(DBReloadSwitcher.class);
    private static final int OPEN = 1;
    private static final int CLOSE = 0;
    private static ScheduledExecutorService executor = ThreadPoolUtil.newSingleDaemonScheduledThreadPool("reload-trace.properties-thread");
    private static volatile int isopen = 0;

    static {

        logger.info("start config reload switcher thread, reload trace.properties file every 10 second");

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            // TODO request remote db server for the value of trace_switch and metric_switch

            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public boolean isTraceOpen() {
        return isopen == OPEN;
    }

    @Override
    public boolean isMetricOpen() {
        return isopen == CLOSE;
    }

    @Override
    public void closeTrace() {

    }

    @Override
    public void closeMetric() {

    }

}
