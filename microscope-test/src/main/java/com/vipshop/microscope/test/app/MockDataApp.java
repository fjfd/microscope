package com.vipshop.microscope.test.app;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.test.app.httpclient.UserHttpClient;
import com.vipshop.microscope.test.app.server.TestWebServer;
import com.vipshop.microscope.trace.Tracer;

public class MockDataApp {

	public static void main(String[] args) throws Exception {
		new Thread(new CollectorServer()).start();

		new TestWebServer(9090).start();

		UserHttpClient userURL = new UserHttpClient();

		while (true) {
			Tracer.cleanContext();
			userURL.findRequest();
			Tracer.cleanContext();
			TimeUnit.MILLISECONDS.sleep(1);
			Tracer.cleanContext();
			userURL.updateRequest();
			TimeUnit.MILLISECONDS.sleep(1);
			Tracer.cleanContext();
			userURL.insertRequest();
			TimeUnit.MILLISECONDS.sleep(1);
			Tracer.cleanContext();
			userURL.deleteRequest();
			TimeUnit.MILLISECONDS.sleep(1);
		}

	}

}
