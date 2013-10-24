package com.vipshop.microscope.trace.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.vipshop.microscope.trace.TraceHttpClient;

public class HTTPTraceTest {
	
	@Test
	public void testHTTPCallTrace() throws ClientProtocolException, IOException {
		TraceHttpClient httpClient = new TraceHttpClient();
		HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/trace/queryCondition?callback=jQuery11020021555292898187584_1382520578214&_=1382520578215");
		httpGet.setHeader("key", "value");
		HttpResponse response = httpClient.executeInTrace(httpGet);
		HttpEntity entity = response.getEntity();
		
		if (null != entity) {
			System.out.println(EntityUtils.toString(entity, "UTF-8"));
			EntityUtils.consume(entity);
		}
	}

}
