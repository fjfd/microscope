package com.vipshop.microscope.test.app;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class UserController implements Runnable {

	private static UserService userService = new UserService();
	
	private CountDownLatch startSignal;
	private Trace contexTrace;
	
	public UserController() {
	}
	
	public UserController(CountDownLatch startSignal, Trace contexTrace) {
		this.startSignal = startSignal;
		this.contexTrace = contexTrace;
	}
	
	public void login() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(new Random(10).nextInt());
		Tracer.clientSend("login-service", Category.SERVICE);
		TimeUnit.MILLISECONDS.sleep(new Random(100).nextInt());
		userService.login();
		Tracer.clientReceive();
	}

	@Override
	public void run() {
		TraceFactory.setContext(contexTrace);
		Trace trace = TraceFactory.getTrace();
		trace.clientSend("login", Category.ACTION);
		try {
			userService.login();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trace.clientReceive();
		startSignal.countDown();
	}
}
