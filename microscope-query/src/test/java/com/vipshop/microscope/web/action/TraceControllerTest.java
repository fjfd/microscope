package com.vipshop.microscope.web.action;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.HttpClientUtil;

public class TraceControllerTest {
	
	@Test
	public void testQueryCondition() throws ClientProtocolException, IOException {
		String url = "http://127.0.0.1:8080/trace/queryCondition?callback=jQuery11020021555292898187584_1382520578214&_=1382520578215";
		String result = HttpClientUtil.request(url);

		System.out.println(result);
	}
	
	@Test
	public void testTraceList() throws ClientProtocolException, IOException {
		String url = "http://127.0.0.1:8080/trace/traceList?appName=picket&traceName=order&startTime=1380168116362000&endTime=1380168116362000&limit=100&callback=alfjdsfadfdk";
		String result = HttpClientUtil.request(url);

		System.out.println(result);
	}
	
	@Test
	public void testTraceSpan() throws ClientProtocolException, IOException {
		String url = "http://127.0.0.1:8080/trace/traceSpan?traceId=-4403393697944183921&callback=%22alfjdsfadfdk%22";
		String result = HttpClientUtil.request(url);

		System.out.println(result);
	}

}
