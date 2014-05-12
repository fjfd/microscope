package com.vipshop.microscope.client.config;

import com.vipshop.microscope.client.Tracer;

/**
 * Config factory
 *
 * 1 file config
 * 2 file reload config
 * 3 zookeeper config
 */
public class Configs {

    private static int key = Tracer.SWITCHER_TYPE;

    public static Config getConfig() {
        return getConfig(key);
    }

    private static Config getConfig(int key) {
        switch (key) {
            case 1:
                return getFileConfig();
            case 2:
                return getFileReloadConfig();
            case 3:
                return getZooKeeperConfig();
            default:
                return getFileConfig();
        }
    }

    private static class FileConfigHolder {
        private static final Config config = new FileConfig();
    }

    public static Config getFileConfig() {
        return FileConfigHolder.config;
    }

    private static class FileReloadConfigHolder {
        private static final Config config = new FileReloadConfig();
    }

    public static Config getFileReloadConfig() {
        return FileReloadConfigHolder.config;
    }

    private static class ZooKeeperConfigHolder {
        private static final Config config = new ZooKeeperConfig();
    }

    public static Config getZooKeeperConfig() {
        return ZooKeeperConfigHolder.config;
    }
}
