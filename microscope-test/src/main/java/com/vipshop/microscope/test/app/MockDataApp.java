package com.vipshop.microscope.test.app;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.test.app.httpclient.UserHttpClient;
import com.vipshop.microscope.test.app.server.TestWebServer;
import com.vipshop.microscope.trace.Tracer;

/**
 * Mock data app
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MockDataApp {

	public static void execute() throws Exception {
		new Thread(new CollectorServer()).start();

		new TestWebServer(9090).start();

		UserHttpClient userURL = new UserHttpClient();

		while (true) {
			Tracer.cleanContext();
			userURL.findRequest();
			Tracer.cleanContext();
			TimeUnit.SECONDS.sleep(1);
			Tracer.cleanContext();
			userURL.updateRequest();
			TimeUnit.SECONDS.sleep(1);
			Tracer.cleanContext();
			userURL.insertRequest();
			TimeUnit.SECONDS.sleep(1);
			Tracer.cleanContext();
			userURL.deleteRequest();
			TimeUnit.SECONDS.sleep(1);
		}

	}

}
