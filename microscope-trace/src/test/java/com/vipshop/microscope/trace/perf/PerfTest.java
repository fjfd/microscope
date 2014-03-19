package com.vipshop.microscope.trace.perf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.trace.Tracer;

public class PerfTest {
	
	@Test
	public void traceUseExampleWithException() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (true) {
						Tracer.cleanContext();
						Tracer.clientSend("example-with-exception", Category.Method);
						try {
							TimeUnit.MILLISECONDS.sleep(0);
							throw new RuntimeException();
						} catch (Exception e) {
							Tracer.setResultCode(e);
						} finally {
							Tracer.clientReceive();
						}
					}
				}
			});
		}
		Thread.currentThread().join();
	}

}
