package com.vipshop.microscope.sample;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.sample.http.UserHttpClient;
import com.vipshop.microscope.sample.server.WebServer;

public class Sample {
	
	public void startSample() throws Exception {
		new Thread(new CollectorServer()).start();
		new WebServer(9090).start();
		
		TimeUnit.SECONDS.sleep(1);
		
		new UserHttpClient().insertRequest();
	}
}
