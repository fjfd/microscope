package com.vipshop.microscope.client.config;

import com.vipshop.microscope.common.util.ConfigurationUtil;

/**
 * Read config file from classpath or zookeeper server.
 *
 * @author Xu Fei
 * @version 1.0
 */
public interface Config {

    boolean hasConfigFile(String file);

    ConfigurationUtil initConfigFile(String file);

    boolean isTraceOpen();

    boolean isMetricOpen();

    void closeTrace();

    void closeMetric();
}
