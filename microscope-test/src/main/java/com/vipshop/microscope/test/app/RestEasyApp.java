package com.vipshop.microscope.test.app;

import com.vipshop.microscope.test.app.httpclient.UserHttpClient;
import com.vipshop.microscope.test.app.server.RestEasyWebServer;

public class RestEasyApp {
	
	public static void main(String[] args) throws Exception {
		RestEasyWebServer server = new RestEasyWebServer(9090);
		
		server.start();
		
		System.out.println(new UserHttpClient().requestRestEasy());;
	}
}
