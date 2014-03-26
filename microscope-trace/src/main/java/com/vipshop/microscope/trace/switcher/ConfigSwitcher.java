package com.vipshop.microscope.trace.switcher;

import com.vipshop.microscope.trace.Tracer;

/**
 * A config switcher
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ConfigSwitcher implements Switcher {
	
	private static final int OPEN = 1;
	private static final int CLOSE = 0;

	@Override
	public boolean isOpen() {
		return Tracer.SWITCH == OPEN;
	}

	@Override
	public boolean isClose() {
		return Tracer.SWITCH == CLOSE;
	}

}
