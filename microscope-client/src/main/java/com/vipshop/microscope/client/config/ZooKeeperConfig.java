package com.vipshop.microscope.client.config;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A zookeeper config
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ZooKeeperConfig extends AbstractConfig implements Config {

    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperConfig.class);
    private static final int OPEN = 1;
    private static final int CLOSE = 0;
    private static volatile int isopen = 0;

    @Override
    public boolean hasConfigFile(String file) {
        return false;
    }

    @Override
    public ConfigurationUtil initConfigFile(String file) {
        return null;
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
