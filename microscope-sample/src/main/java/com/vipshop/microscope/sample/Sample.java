package com.vipshop.microscope.sample;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.sample.http.UserHttpClient;
import com.vipshop.microscope.sample.server.WebServer;

public class Sample {
	
	public void startSample() throws Exception {
//		boolean startCollector = true;
//		boolean startWebServer = true;

		boolean startCollector = false;
		boolean startWebServer = false;

		if (startCollector) {
			new Thread(new CollectorServer()).start();
		}
		if (startWebServer) {
			new WebServer(9090).start();
		}
		
		new UserHttpClient().insertRequest();
		TimeUnit.SECONDS.sleep(1);
	}
	
	public static void main(String[] args) throws Exception {
		Sample sample = new Sample();
		sample.startSample();
		TimeUnit.SECONDS.sleep(5);
	}
}
