package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.trace.span.Category;
import com.vipshop.microscope.trace.span.ResultCode;

public class TraceTest {
	
	@Test
	public void traceUseExample() throws InterruptedException {
		Tracer.clientSend("example", Category.METHOD);
		
		try {
			TimeUnit.MILLISECONDS.sleep(10);
			System.out.println("example method invoke");
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
	}
	
	@Test
	public void traceUseExample1() throws InterruptedException {
		while (true) {
			Tracer.cleanContext();
			Tracer.clientSend("example1", Category.METHOD);
			
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
