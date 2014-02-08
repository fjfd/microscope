package com.vipshop.microscope.trace.sample;

/**
 * Sampler use for reduce tracing overhead. 
 * 
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Sampler {
	
	public boolean sample();
	
}
