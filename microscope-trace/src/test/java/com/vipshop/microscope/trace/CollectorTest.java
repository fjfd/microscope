package com.vipshop.microscope.trace;

import org.testng.annotations.Test;

public class CollectorTest {

	@Test
	public void test() {
		new UserService().login();
	}

}
