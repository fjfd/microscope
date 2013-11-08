package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.trace.span.Category;

public class TraceTest {
	
	@BeforeClass
	public void setUpBeforeClass() {
		new Thread(new CollectorServer()).start();
	}
	
	@Test
	public void traceUseExample() throws InterruptedException {
		
		Tracer.clientSend("example1", Category.METHOD);
		
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("example method invoke");
			throw new RuntimeException();
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		
		TimeUnit.SECONDS.sleep(10);
	}
	
}
