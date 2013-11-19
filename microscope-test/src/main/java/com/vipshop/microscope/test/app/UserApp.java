package com.vipshop.microscope.test.app;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.test.app.httpclient.UserHttpClient;
import com.vipshop.microscope.test.app.server.TestWebServer;

public class UserApp {

	private static TestWebServer testWebServer = new TestWebServer(9090);

	public static void main(String[] args) throws Exception {

		new Thread(new CollectorServer()).start();

		testWebServer.start();

		UserHttpClient userURL = new UserHttpClient();

		userURL.insertRequest();

		// TimeUnit.SECONDS.sleep(5);
	}
}
