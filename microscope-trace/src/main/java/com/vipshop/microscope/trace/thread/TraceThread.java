package com.vipshop.microscope.trace.thread;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.Tracer;


public class TraceThread extends Thread {
	
	private Trace context;
	
	public TraceThread(Trace context) {
		this.context = context;
	}
	
	@Override
	public void run() {
		Tracer.setContext(context);
		
	}	
	
}
