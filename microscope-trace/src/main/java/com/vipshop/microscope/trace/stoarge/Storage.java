package com.vipshop.microscope.trace.stoarge;

import com.vipshop.microscope.framework.thrift.Span;

/**
 * Storge span in client.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public interface Storage {
	
	public void add(Span span);
	
	public Span poll();
	
	public int size();
}
