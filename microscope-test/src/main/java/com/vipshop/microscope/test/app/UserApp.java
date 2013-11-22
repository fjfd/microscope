package com.vipshop.microscope.test.app;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.test.app.httpclient.UserHttpClient;
import com.vipshop.microscope.test.app.server.TestWebServer;
import com.vipshop.microscope.trace.Tracer;

public class UserApp {

	public static void main(String[] args) throws Exception {

		new Thread(new CollectorServer()).start();

		new TestWebServer(9090).start();
		
		UserHttpClient userURL = new UserHttpClient();

		while (true) {
			userURL.findRequest();
			Tracer.cleanContext();
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

	}
}
