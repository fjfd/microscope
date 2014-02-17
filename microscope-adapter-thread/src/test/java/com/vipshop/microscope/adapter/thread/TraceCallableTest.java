package com.vipshop.microscope.adapter.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.framework.span.Category;
import com.vipshop.microscope.trace.Tracer;

public class TraceCallableTest {
	
	@Test
	public void testCallable() throws Exception {
		
		Tracer.clientSend("callable-test", Category.Method);
		
		TimeUnit.SECONDS.sleep(1);
		
		Callable<Integer> callable = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				
				Tracer.clientSend("call", Category.Method);
				
				TimeUnit.SECONDS.sleep(1);
				
				Tracer.clientReceive();
				return 1;
			}
		};
		
		Executors.newSingleThreadExecutor().submit(callable);
		
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(3);
	}
	
	@Test
	public void testTraceCallable() throws Exception {
		
		Tracer.clientSend("trace-callable-test", Category.Method);
		
		TimeUnit.SECONDS.sleep(1);
		
		TraceCallable<Integer> callable = new TraceCallable<Integer>(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				TimeUnit.SECONDS.sleep(1);
				return 1;
			}
		});
		
		Executors.newSingleThreadExecutor().submit(callable);
		
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(3);
	}
	


}
