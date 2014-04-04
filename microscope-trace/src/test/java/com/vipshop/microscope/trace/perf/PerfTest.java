package com.vipshop.microscope.trace.perf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.trace.Tracer;

public class PerfTest {

	@Test
	public void testTrace() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						Tracer.cleanContext();
						Tracer.clientSend("trace-perf-test", Category.Method);
						try {
							TimeUnit.MILLISECONDS.sleep(1);
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
	
	@Test
	public void testNetWork() throws InterruptedException {
		while (true) {
			Tracer.cleanContext();
			Tracer.clientSend("trace-perf-test", Category.Method);
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (Exception e) {
				Tracer.setResultCode(e);
			} finally {
				Tracer.clientReceive();
			}
			TimeUnit.SECONDS.sleep(1);
		}
	}

}
