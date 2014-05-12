package com.vipshop.microscope.client.configurator;

import com.vipshop.microscope.client.Tracer;
import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
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
public class FileReloadConfigurator extends AbstractConfigurator implements Configurator {

    private static final Logger logger = LoggerFactory.getLogger(FileReloadConfigurator.class);

    @Override
    public void setConfig(ConfigurationUtil properties) {

        configurationUtil = properties;

        ScheduledExecutorService executor = ThreadPoolUtil.newSingleDaemonScheduledThreadPool("config-file-reload-thread");
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                configurationUtil = ConfigurationUtil.getConfiguration(Configurators.DEFAULT_CONFIG_NAME);
            }

        }, 10, 10, TimeUnit.SECONDS);

    }

    @Override
    public String getAppName() {
        return configurationUtil.getString("app_name");
    }

    @Override
    public String getCollectorHost() {
        return configurationUtil.getString("collector_host");
    }

    @Override
    public int getCollectorPort() {
        return configurationUtil.getInt("collector_port");
    }

    @Override
    public int getMaxBatchSize() {
        return configurationUtil.getInt("max_batch_size");
    }

    @Override
    public int getMaxEmptySize() {
        return configurationUtil.getInt("max_empty_size");
    }

    @Override
    public int getTraceSwitch() {
        return configurationUtil.getInt("trace_switch");
    }

    @Override
    public int getMetricSwitch() {
        return configurationUtil.getInt("metric_switch");
    }

    @Override
    public int getSystemSwitch() {
        return configurationUtil.getInt("system_switch");
    }

    @Override
    public int getExceptionSwitch() {
        return configurationUtil.getInt("exception_switch");
    }

    @Override
    public int getTransportSwitch() {
        return configurationUtil.getInt("transport_switch");
    }

    @Override
    public int getQueueSize() {
        return configurationUtil.getInt("queue_size");
    }

    @Override
    public int getReconnectWaitTime() {
        return configurationUtil.getInt("reconnect_wait_time");
    }

    @Override
    public int getSendWaitTime() {
        return configurationUtil.getInt("send_wait_time");
    }

    @Override
    public int getReportPeriodTime() {
        return configurationUtil.getInt("report_period_time");
    }

    @Override
    public int getStorageType() {
        return configurationUtil.getInt("storage_type");
    }

    @Override
    public int getSamplerType() {
        return configurationUtil.getInt("sampler_type");
    }

    @Override
    public boolean isTraceOpen() {
        return getTraceSwitch() == TRACE_OPEN;
    }

    @Override
    public boolean isMetricOpen() {
        return getMetricSwitch() == METRIC_OPEN;
    }

    @Override
    public boolean isSystemOpen() {
        return getSystemSwitch() == SYSTEM_OPEN;
    }

    @Override
    public boolean isExceptionOpen() {
        return getExceptionSwitch() == EXCEPTION_OPEN;
    }

    @Override
    public boolean isTransportOpen() {
        return getTransportSwitch() == TRANSPORT_OPEN;
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
