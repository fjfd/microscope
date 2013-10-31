package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

public class CollectorTest {

	@Test
	public void test() throws InterruptedException {
		new UserService().login();
		TimeUnit.SECONDS.sleep(60);
	}

}
