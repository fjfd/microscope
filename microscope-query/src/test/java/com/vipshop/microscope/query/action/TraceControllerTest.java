package com.vipshop.microscope.query.action;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.HttpClientUtil;
import com.vipshop.microscope.query.server.QueryServer;

public class TraceControllerTest {
	
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
	public void testQueryCondition() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/trace/queryCondition?callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testTraceList() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/trace/traceList?appName=picket&traceName=order&startTime=1380168116362000&endTime=1380168116362000&limit=100&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
	
	@Test
	public void testTraceSpan() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/trace/traceSpan?traceId=-4403393697944183921&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}

}
