package com.vipshop.microscope.trace.switcher;

import com.vipshop.microscope.trace.Tracer;

/**
 * Switcher factory
 *
 * 1 ConfigSwitcher
 * 2 ConfigReloadSwitcher
 * 3 DBReloadSwitcher
 */
public class SwitcherHolder {

    private static int key = Tracer.SWITCHER_TYPE;

    public static Switcher getSwitcher() {
        return getSwicther(key);
    }

    private static Switcher getSwicther(int key) {
        switch (key) {
            case 1:
                return getConfigSwitcher();
            case 2:
                return getConfigReloadSwitcher();
            case 3:
                return getDBReloadSwitcher();
            default:
                return getConfigSwitcher();
        }
    }

    private static class ConfigSwitcherHolder {
        private static final Switcher switcher = new ConfigSwitcher();
    }

    public static Switcher getConfigSwitcher() {
        return ConfigSwitcherHolder.switcher;
    }

    private static class ConfigReloadSwitcherHolder {
        private static final Switcher switcher = new ConfigReloadSwitcher();
    }

    public static Switcher getConfigReloadSwitcher() {
        return ConfigReloadSwitcherHolder.switcher;
    }

    private static class DBReloadSwitcherHolder {
        private static final Switcher switcher = new DBReloadSwitcher();
    }

    public static Switcher getDBReloadSwitcher() {
        return DBReloadSwitcherHolder.switcher;
    }
}
