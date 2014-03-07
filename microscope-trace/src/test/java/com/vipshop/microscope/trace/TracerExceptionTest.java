package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Category;

public class TracerExceptionTest {
	
	@Test
	public void testRecordException() throws InterruptedException {
		Tracer.clientSend("testRecordException", Category.Method);
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
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(3);
	}
	
	@Test
	public void testRecordExceptionWithInfo() throws InterruptedException {
		Tracer.cleanContext();
		Tracer.clientSend("testRecordExceptionWithInfo", Category.Method);
		int sum = 0;
		for (int i = 0; i < 1000; i++) {
			sum += i;
		}
		try {
			if (sum > 1000) {
				throw new RuntimeException("sum > 1000");
			}
			
		} catch (Exception e) {
			Tracer.record(e, "this is a debug info : sum > 1000");
		}
		Tracer.clientReceive();
		TimeUnit.SECONDS.sleep(3);
	}

}
