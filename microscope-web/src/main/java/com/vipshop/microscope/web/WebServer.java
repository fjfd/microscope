package com.vipshop.microscope.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class WebServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(80);
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("src/main/resources");
		server.setHandler(resourceHandler);
		server.start();
	}
}
