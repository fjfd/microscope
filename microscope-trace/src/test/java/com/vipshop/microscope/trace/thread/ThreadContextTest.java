package com.vipshop.microscope.trace.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.testng.annotations.Test;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class ThreadContextTest {
	
	@Test
	public void testContext() {
		
		Tracer.clientSend("main thread", Category.METHOD);
		
		ExecutorService executor = ThreadPoolFactory.newFixedThreadPool(1, Tracer.getContext());
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					System.out.println(Tracer.getContext());
				}
				
			}
		});
		
		Tracer.clientReceive();
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Tracer.clientSend("main thread", Category.METHOD);
		
		final CountDownLatch startSignal = new CountDownLatch(1);
		final Trace context = Tracer.getContext();
		System.out.println(Thread.currentThread().getName());
		System.err.println(Tracer.getContext());
		ExecutorService executor = ThreadPoolFactory.newFixedThreadPool(1, Tracer.getContext());
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				Tracer.setContext(context);
				System.out.println(Tracer.getContext());
				startSignal.countDown();
			}
		});
		startSignal.await();
		Tracer.clientReceive();
		System.exit(0);
	}
}
