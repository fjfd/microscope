package com.vipshop.microscope.query.action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.HttpClientUtil;
import com.vipshop.microscope.query.server.QueryServer;

public class ReportControllerTest {
	
	QueryServer webServer = new QueryServer(8080);
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		webServer.start();
	}
	
	@AfterClass
	public void tearDownAfterClass() throws Exception {
		webServer.stop();
	}
	
	@Test
	public void testGetMsgReport() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/msgReport?year=2013&month=11&week=4&day=21&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testAppName() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/appAndIP?&callback?&callback=jQuery1102036526327672395875_1385541924178&_=1385541924179";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}

	@Test
	public void testTraceReport() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/traceReport?appName=picket&ipAdress=10.101.3.169&year=2013&month=11&week=4&day=18&hour=6&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testTraceReportByType() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/traceReport?appName=picket&ipAdress=10.101.3.169&type=DB&year=2013&month=11&week=4&day=18&hour=6&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testOverTime() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/overTimeReport?appName=picket&ipAdress=10.101.3.169&type=DB&year=2013&month=11&week=4&day=18&hour=6&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testOverTimeUseName() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/overTimeReport?appName=picket&ipAdress=10.101.3.169&type=DB&name=db&year=2013&month=11&week=4&day=18&hour=6&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testSourceReport() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/sourceReport?year=2013&month=11&week=4&day=25&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testJVMMetrics() throws ClientProtocolException, IOException, InterruptedException {
		while (true) {
			String url = "http://localhost:8080/report/jvmMetrics?appName=trace&ipAddress=10.101.3.111&callback=jQuery11020021555292898187584";
			String result = HttpClientUtil.request(url);
			System.out.println(result);
			TimeUnit.SECONDS.sleep(5);
		}
	}
	
}
