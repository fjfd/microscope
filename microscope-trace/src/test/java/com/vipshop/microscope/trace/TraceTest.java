package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.trace.span.Category;

public class TraceTest {
	
	@Test
	public void traceUseExample() throws InterruptedException {
		
		Tracer.clientSend("example", Category.METHOD);
		
		try {
			System.out.println("method invoke");
			throw new RuntimeException();
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		}
		
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(10);
	}
	
}
