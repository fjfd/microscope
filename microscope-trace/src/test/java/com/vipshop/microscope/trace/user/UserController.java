package com.vipshop.microscope.trace.user;

import java.util.concurrent.CountDownLatch;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;
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
	
	public void login() {
		Trace trace = TraceFactory.getTrace();

		trace.clientSend("login", Category.ACTION);
		userService.login();
		trace.clientReceive();
	}

	@Override
	public void run() {
		TraceFactory.setContext(contexTrace);
		Trace trace = TraceFactory.getTrace();

		trace.clientSend("login", Category.ACTION);
		userService.login();
		trace.clientReceive();
		startSignal.countDown();
	}
}
