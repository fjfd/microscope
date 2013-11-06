package com.vipshop.microscope.framework.thread;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.Tracer;

public class RunableTask implements Runnable {

	private Trace contexTrace;
	
	public RunableTask(Trace contexTrace) {
		this.contexTrace = contexTrace;
	}
	
	@Override
	public void run() {
		Tracer.setContext(contexTrace);
	}
}
