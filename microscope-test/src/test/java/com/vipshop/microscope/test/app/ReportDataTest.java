package com.vipshop.microscope.test.app;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.test.app.UserController;
import com.vipshop.microscope.trace.ResultCode;
import com.vipshop.microscope.trace.TraceFactory;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;
import com.vipshop.microscope.web.server.JettyWebServer;

public class ReportDataTest {

	@BeforeClass
	public void setUp() throws Exception {
		new Thread(new CollectorServer()).start();
		new JettyWebServer(8080).start();
	}

	@Test
	public synchronized void test() throws InterruptedException, ClientProtocolException, IOException {
		while (true) {
			TraceFactory.cleanContext();
			// send data
			Tracer.clientSend("http://localhost/vipshop/microscope/trace", Category.ACTION);
			try {
				TimeUnit.MILLISECONDS.sleep(new Random(100).nextInt());
				new UserController().login();
			} catch (Exception e) {
				Tracer.setResultCode(ResultCode.EXCEPTION);
			} finally {
				Tracer.clientReceive();
			}
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
