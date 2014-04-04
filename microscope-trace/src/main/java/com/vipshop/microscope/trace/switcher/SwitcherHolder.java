package com.vipshop.microscope.trace.switcher;

public class SwitcherHolder {
	
	public static Switcher getConfigSwitcher() {
		return new ConfigSwitcher();
	}
	
	public static Switcher getConfigReloadSwitcher() {
		return new ConfigReloadSwitcher();
	}
	
	public static Switcher getDynamicSwitcher() {
		return new DynamicSwitcher();
	}
}
