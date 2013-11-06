package com.vipshop.microscope.framework.thread;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.ResultCode;
import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class RunableTask implements Runnable {

	private Trace contexTrace;
	
	public RunableTask(Trace contexTrace) {
		this.contexTrace = contexTrace;
	}
	
	@Override
	public void run() {
		Tracer.setContext(contexTrace);
		
		Tracer.clientSend("read", Category.METHOD);
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		
	}
}
