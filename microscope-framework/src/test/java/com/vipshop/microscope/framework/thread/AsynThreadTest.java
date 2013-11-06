package com.vipshop.microscope.framework.thread;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.trace.ResultCode;
import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class AsynThreadTest {
	
	@BeforeClass
	public void setUp() {
		new Thread(new CollectorServer()).start();
	}
	
	@AfterClass
	public void tearDown() {
		System.exit(0);
	}
	
	
	@Test
	public void testAsynThread() throws InterruptedException {
		Tracer.clientSend("user-login-new-thread", Category.ACTION);
		try {
			Trace contexTrace = Tracer.getContext();
			new Thread(new RunableTask(contexTrace)).start();
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		
		TimeUnit.SECONDS.sleep(5);
	}
	
}
