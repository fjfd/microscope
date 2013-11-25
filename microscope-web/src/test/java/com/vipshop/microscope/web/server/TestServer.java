package com.vipshop.microscope.web.server;

import org.testng.annotations.Test;

public class TestServer {
	
	@Test
	public void testServer() {
		new Thread(new HttpServer()).start();
	}
}
