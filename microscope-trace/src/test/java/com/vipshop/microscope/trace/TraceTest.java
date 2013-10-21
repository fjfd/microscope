package com.vipshop.microscope.trace;

import java.util.concurrent.CountDownLatch;

import org.testng.annotations.Test;

import com.vipshop.microscope.trace.user.UserController;

public class TraceTest {
	
	/**
	 * A trace which all spans in one thread.
	 * 
	 * @throws InterruptedException
	 */
	@Test(priority = 1)
	public void testTrace() throws InterruptedException {
		Tracer.clientSend("user-login");
		new UserController().login();
		Tracer.clientReceive();
	}
	
	/**
	 * A trace which some spans in new thread.
	 * 
	 * @throws InterruptedException
	 */
	@Test(priority = 2)
	public void testTraceStartNewThread() throws InterruptedException {
		CountDownLatch startSignal = new CountDownLatch(1);
		Tracer.clientSend("user-login-new-thread");
		Trace contexTrace = Tracer.getContext();
		new Thread(new UserController(startSignal, contexTrace)).start();
		startSignal.await();
		Tracer.clientReceive();
	}
	
	/**
	 * A trace which some spans cross JVM.
	 * 
	 */
	@Test(priority = 3)
	public void testTraceCrossJVM() {
		
	}

}
