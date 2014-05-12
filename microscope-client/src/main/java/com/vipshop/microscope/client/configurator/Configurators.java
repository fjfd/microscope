package com.vipshop.microscope.client.configurator;

import com.vipshop.microscope.common.util.ConfigurationUtil;

/**
 * Config factory
 *
 * 1 file config
 * 2 file reload config
 * 3 zookeeper config
 */
public class Configurators {

    /**
     * Default app name
     */
    public static String DEFAULT_APP_NAME = "default-name";

    /**
     * Default collector host
     */
    public static String DEFAULT_COLLECTOR_HOST = "10.19.111.64";

    /**
     * Default collector port
     */
    public static int DEFAULT_COLLECTOR_PORT = 9410;

    /**
     * Default batch send spans size
     */
    public static int DEFAULT_MAX_BATCH_SIZE = 100;

    /**
     * Default retry times when no data come
     */
    public static int DEFAULT_MAX_EMPTY_SIZE = 100;

    /**
     * Default close collect trace function
     */
    public static volatile int DEFAULT_TRACE_SWITCH = 0;

    /**
     * Default close collect metric function
     */
    public static volatile int DEFAULT_METRIC_SWITCH = 0;

    /**
     * Default close collect system data function
     */
    public static volatile int DEFAULT_SYSTEM_SWITCH = 0;

    /**
     * Default close collect exception data function
     */
    public static volatile int DEFAULT_EXCEPTION_SWITCH = 0;

    /**
     * Default close transport data function
     */
    public static volatile int DEFAULT_TRANSPORT_SWITCH = 0;

    /**
     * Default client queue size
     */
    public static int DEFAULT_QUEUE_SIZE = 10000;

    /**
     * Default reconnect time for thrift client
     */
    public static int DEFAULT_RECONNECT_WAIT_TIME = 3000;

    /**
     * Default wait time for transporter thread
     */
    public static int DEFAULT_SEND_WAIT_TIME = 100;

    /**
     * Default period time for metrics reporter
     */
    public static int DEFAULT_REPORT_PERIOD_TIME = 10;

    /**
     * Use {@code ArrayBlockingQueueStorage} as default storage
     */
    public static int DEFAULT_STORAGE_TYPE = 1;

    /**
     * Use {@code AllSampler} as default sampler
     */
    public static int DEFAULT_SAMPLER_TYPE = 1;

    /**
     * Config properties file name
     */
    public static final String DEFAULT_CONFIG_NAME = "microscope.properties";


    public static boolean hasConfigFile(String file) {
        return ConfigurationUtil.fileExist(file);
    }

    public static Configurator getConfig(String file) {
        ConfigurationUtil properties = ConfigurationUtil.getConfiguration(file);
        int key = properties.getInt("config_type");
        return getConfig(key, properties);
    }

    private static Configurator getConfig(int key, ConfigurationUtil properties) {
        switch (key) {
            case 1:
                return getFileConfig(properties);
            case 2:
                return getFileReloadConfig(properties);
            case 3:
                return getZooKeeperConfig(properties);
            default:
                return getFileConfig(properties);
        }
    }

    private static class FileConfigHolder {
        private static final Configurator config = new FileConfigurator();
    }

    public static Configurator getFileConfig(ConfigurationUtil properties) {
        Configurator config = FileConfigHolder.config;
        config.setConfig(properties);
        return config;
    }

    private static class FileReloadConfigHolder {
        private static final Configurator config = new FileReloadConfigurator();
    }

    public static Configurator getFileReloadConfig(ConfigurationUtil properties) {
        Configurator config = FileReloadConfigHolder.config;
        config.setConfig(properties);
        return config;
    }

    private static class ZooKeeperConfigHolder {
        private static final Configurator config = new ZooKeeperConfigurator();
    }

    public static Configurator getZooKeeperConfig(ConfigurationUtil properties) {
        Configurator config = ZooKeeperConfigHolder.config;
        config.setConfig(properties);
        return config;
    }
}
