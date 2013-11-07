package com.vipshop.microscope.test.app;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.common.util.HttpClientUtil;
import com.vipshop.microscope.trace.ResultCode;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class ExampleTest {
	
	@BeforeClass
	public void setUp() throws Exception {
		new Thread(new CollectorServer()).start();
//		new WebServer(8080).start();
	}
	
	@Test
	public void test() throws InterruptedException, ClientProtocolException, IOException {
		
		// send data
		Tracer.clientSend("example", Category.METHOD);
		try {
			new UserController().login();
			throw new RuntimeException();
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(5);
		
		// query data
		String url = "http://localhost:8080/trace/queryCondition?callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		
		// display data
		System.out.println(result);
	}
}
