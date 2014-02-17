package com.vipshop.microscope.adapter.thread;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.framework.span.Category;
import com.vipshop.microscope.trace.Tracer;

public class TraceRunableTest {
	
	@Test
	public void testRunable() throws InterruptedException {
		
		Tracer.clientSend("test-runalbe", Category.Method);
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				Tracer.clientSend("runable", Category.Method);
				
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Tracer.clientReceive();
				
			}
		};
		
		new Thread(runnable).start();
		
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(3);
	}
	
	@Test
	public void testTraceRunnable() throws InterruptedException {
		Tracer.clientSend("test-trace-runnable", Category.Method);
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		
		new Thread(new TraceRunnable(runnable)).start();
		
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(3);

	}
}
