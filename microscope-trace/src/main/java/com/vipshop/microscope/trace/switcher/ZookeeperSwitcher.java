package com.vipshop.microscope.trace.switcher;

import com.vipshop.microscope.trace.Tracer;

/**
 * A flag to judge trace function is on/off.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ZookeeperSwitcher {
	
	private static final int OPEN = 1;
	private static final int CLOSE = 0;
	
	
	public static boolean isOpen() { 
		return Tracer.SWITCH == OPEN;
	}
	
	public static boolean isClose() { 
		return Tracer.SWITCH == CLOSE;
	}
}
