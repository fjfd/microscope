package com.vipshop.microscope.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

import com.vipshop.microscope.query.QueryServer;

public class WebServer {

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new QueryServer().start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Server server = new Server(80);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("E:\\Workspace\\microscope-web");
        server.setHandler(resourceHandler);
        server.start();

    }
}
