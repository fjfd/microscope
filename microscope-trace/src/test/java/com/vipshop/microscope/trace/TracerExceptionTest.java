package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

public class TracerExceptionTest {
	
	@Test
	public void testRecordException() throws InterruptedException {
		int sum = 0;
		
		for (int i = 0; i < 1000; i++) {
			sum += i;
		}
		
		try {
			if (sum > 1000) {
				throw new RuntimeException("sum > 1000");
			}
			
		} catch (Exception e) {
			Tracer.record(e);
		}
		
		TimeUnit.SECONDS.sleep(3);
	}
	
	@Test
	public void testRecordExceptionWithInfo() throws InterruptedException {
		int sum = 0;
		
		for (int i = 0; i < 1000; i++) {
			sum += i;
		}
		
		try {
			if (sum > 1000) {
				throw new RuntimeException("sum > 1000");
			}
			
		} catch (Exception e) {
			Tracer.record(e, "sum > 1000");
		}
		
		TimeUnit.SECONDS.sleep(3);
	}

}
