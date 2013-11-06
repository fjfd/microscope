package com.vipshop.microscope.test.trace;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.test.app.UserController;
import com.vipshop.microscope.trace.ResultCode;
import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class TraceTest {
	
	@BeforeClass
	public void setUp() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(new CollectorServer());
	}
	
	@AfterClass
	public void tearDown() {
		System.exit(0);
	}
	
	@Test
	public void traceUseExample() throws InterruptedException {
		Tracer.clientSend("example", Category.METHOD);
		try {
			new UserController().login();
			throw new RuntimeException();
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(10);
	}
	
	/**
	 * A trace which all spans in one thread.
	 * 
	 * @throws InterruptedException
	 */
	@Test(priority = 1)
	public void makeTrace() throws InterruptedException {
		for (int i = 0; i < 20; i++) {
			TraceFactory.cleanContext();
			new UserController().login();
		}
		TimeUnit.SECONDS.sleep(5);
	}
	
	/**
	 * A trace which some spans in new thread.
	 * 
	 * @throws InterruptedException
	 */
	@Test(priority = 2)
	public void testTraceStartNewThread() throws InterruptedException {
		CountDownLatch startSignal = new CountDownLatch(1);
		Tracer.clientSend("user-login-new-thread", Category.ACTION);
		Trace contexTrace = Tracer.getContext();
		new Thread(new UserController(startSignal, contexTrace)).start();
		startSignal.await();
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(50);
	}
	
}
