package com.vipshop.microscope.test.app;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.test.app.httpclient.UserHttpClient;
import com.vipshop.microscope.test.app.server.TestWebServer;
import com.vipshop.microscope.trace.Tracer;

public class UserApp {

	private static TestWebServer testWebServer = new TestWebServer(9090);

	public static void main(String[] args) throws Exception {

		new Thread(new CollectorServer()).start();

		testWebServer.start();
		
		UserHttpClient userURL = new UserHttpClient();

		while (true) {
			Tracer.cleanContext();
			userURL.findRequest();
			TimeUnit.MICROSECONDS.sleep(1000);
			Tracer.cleanContext();
			userURL.updateRequest();
			TimeUnit.MICROSECONDS.sleep(1000);
			Tracer.cleanContext();
			userURL.insertRequest();
			TimeUnit.MICROSECONDS.sleep(1000);
			Tracer.cleanContext();
			userURL.deleteRequest();
			TimeUnit.MICROSECONDS.sleep(1000);
		}

		// TimeUnit.SECONDS.sleep(5);
	}
}
