package com.vipshop.microscope.trace.thrift;

import com.vipshop.microscope.trace.gen.LogEntry;
import com.vipshop.microscope.trace.gen.ResultCode;
import com.vipshop.microscope.trace.gen.Send;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThriftTest {

    public static void main(String[] args) throws TTransportException {
        int type = Integer.valueOf(System.getProperty("type"));
        if (type == 1) {
            new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.SIMPLE);
        }
        if (type == 2) {
            new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.NON_BLOCKING);
        }
        if (type == 3) {
            new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.HS_HA);
        }
        if (type == 4) {
            new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.THREAD_POOL);
        }
        if (type == 5) {
            new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.THREAD_SELECTOR);
        }
    }

    @Test
    public void testThreadSelectorThriftServer() throws TException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ThriftServer(new SimpleHandler(), 9410).startServer(ThriftCategory.THREAD_SELECTOR);
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
        LogEntry logEntry = new LogEntry("test", "message");

        ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.THREAD_SELECTOR);
        client.send(Arrays.asList(logEntry));
    }

    static class SimpleHandler implements Send.Iface {
        @Override
        public ResultCode send(List<LogEntry> messages) throws TException {
            for (LogEntry logEntry : messages) {
                Assert.assertEquals("message", logEntry.getMessage());
            }
            return ResultCode.OK;
        }
    }

}
