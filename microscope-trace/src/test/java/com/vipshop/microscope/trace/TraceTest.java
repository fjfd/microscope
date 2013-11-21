package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.trace.span.Category;

public class TraceTest {
	
	@BeforeClass
	public void setUpBeforeClass() {
	}
	
	@Test
	public void traceUseExample() throws InterruptedException {
		while (true) {
			TraceFactory.cleanContext();
			Tracer.clientSend("example2", Category.METHOD);
			
			try {
				TimeUnit.MILLISECONDS.sleep(10);
				System.out.println("example method invoke");
			} catch (Exception e) {
				Tracer.setResultCode(ResultCode.EXCEPTION);
			} finally {
				Tracer.clientReceive();
			}
			
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
}
