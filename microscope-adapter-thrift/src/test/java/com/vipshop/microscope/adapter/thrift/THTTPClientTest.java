package com.vipshop.microscope.adapter.thrift;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;

public class THTTPClientTest {

	public static void main(String[] args) throws TTransportException, InterruptedException {
		THttpClient client = new THttpClient("http://localhost:9090/thrift/hand?callback=jQuery11020021555292898187584");
		client.flush();

		TimeUnit.SECONDS.sleep(10);
	}
}
