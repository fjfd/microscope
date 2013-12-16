package com.vipshop.microscope.sample;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.sample.http.UserHttpClient;
import com.vipshop.microscope.sample.server.WebServer;

public class Sample {
	public static void main(String[] args) throws Exception {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new CollectorServer();
			}
		}).start();
		
		new WebServer(9090).start();
		
		new UserHttpClient().insertRequest();
		
	}
}
