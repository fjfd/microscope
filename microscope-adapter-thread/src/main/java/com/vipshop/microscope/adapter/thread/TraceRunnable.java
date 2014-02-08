package com.vipshop.microscope.adapter.thread;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.Tracer;

public class TraceRunnable implements Runnable {
	
	private final Trace parent;
	private final Runnable runnable;

	public TraceRunnable(Runnable r) {
		this.parent = Tracer.getContext();
		this.runnable = r;
	}

	public TraceRunnable(Runnable r, Trace p) {
		this.runnable = r;
		this.parent = p;
	}

	@Override
	public void run() {
		if (parent != null) {
			Tracer.setContext(parent);
		}
		
		Tracer.clientSend(Thread.currentThread().getName() + "-" + Thread.currentThread().getId(), Category.Method);
		runnable.run();
		Tracer.clientReceive();
	}

}