package com.vipshop.microscope.web;

import com.vipshop.microscope.query.QueryServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.testng.annotations.Test;

public class WebServerTest {

    @Test
    public void testWebServer1() throws Exception {
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

        Thread.currentThread().join();

    }

    @Test
    public void testWebServer2() throws Exception {
        Server server = new Server(80);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("E:\\Workspace\\microscope-web");
        server.setHandler(resourceHandler);
        server.start();

        Thread.currentThread().join();

    }

}
