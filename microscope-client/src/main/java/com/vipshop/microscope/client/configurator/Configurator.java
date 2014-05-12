package com.vipshop.microscope.client.configurator;

import com.vipshop.microscope.common.util.ConfigurationUtil;

/**
 * Read config file from classpath or zookeeper server.
 *
 * @author Xu Fei
 * @version 1.0
 */
public interface Configurator {

    void setConfig(ConfigurationUtil propertie);

    String getAppName();

    String getCollectorHost();

    int getCollectorPort();

    int getMaxBatchSize();

    int getMaxEmptySize();

    int getTraceSwitch();

    int getMetricSwitch();

    int getSystemSwitch();

    int getExceptionSwitch();

    int getTransportSwitch();

    int getQueueSize();

    int getReconnectWaitTime();

    int getSendWaitTime();

    int getReportPeriodTime();

    int getStorageType();

    int getSamplerType();

    boolean isTraceOpen();

    boolean isMetricOpen();

    boolean isSystemOpen();

    boolean isExceptionOpen();

    boolean isTransportOpen();

    void closeTrace();

    void closeMetric();
}
