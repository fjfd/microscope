package com.vipshop.microscope.web.action;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.HttpClientUtil;
import com.vipshop.microscope.web.server.JettyWebServer;

public class ReportControllerTest {
	
	JettyWebServer webServer = new JettyWebServer(8080);
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		webServer.start();
	}
	
	@AfterClass
	public void tearDownAfterClass() throws Exception {
		webServer.stop();
	}

	@Test
	public void testGetReportUseName() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/traceReportUseName?callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testGetReportUseName1() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/traceReportUseName?name=example-id-0&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}

	
	@Test
	public void testGetReportUseType() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/traceReportUseType?callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
}
