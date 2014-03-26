package com.vipshop.microscope.trace.switcher;

/**
 * A flag to turn trace function on/off.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Switcher {
	
	public boolean isOpen();
	
	public boolean isClose();
}
