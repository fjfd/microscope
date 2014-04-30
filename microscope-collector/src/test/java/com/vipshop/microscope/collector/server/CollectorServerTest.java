package com.vipshop.microscope.collector.server;

import com.vipshop.microscope.collector.CollectorServer;
import org.apache.thrift.transport.TTransportException;
import org.testng.annotations.Test;

public class CollectorServerTest {

    @Test
    public void startServer() throws TTransportException {
        CollectorServer server = new CollectorServer();
        server.start();
    }
}
