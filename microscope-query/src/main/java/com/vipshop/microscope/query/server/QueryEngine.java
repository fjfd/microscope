package com.vipshop.microscope.query.server;


public class QueryEngine {
	
	public static void main(String[] args) throws Exception {
		int port = 8080;
		String newPort = System.getProperty("port");
		if (newPort != null) {
			port = Integer.valueOf(newPort);
		}
		new WebServer(port).start();
	}

}
