package com.vipshop.microscope.test.app;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.test.app.server.TestWebServer;
import com.vipshop.microscope.test.app.url.UserURL;

public class UserApp {
	
	private static TestWebServer testWebServer = new TestWebServer(9090);
	
	public static void main(String[] args) throws Exception {
		
		new Thread(new CollectorServer()).start();
		
		testWebServer.start();
		
		UserURL userURL = new UserURL();
		
		userURL.insertRequest();
		
		TimeUnit.SECONDS.sleep(5);
	}
}
