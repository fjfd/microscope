package com.vipshop.microscope.test.trace;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

public class HttpClientTest {
	
	@Test
	public void testQueryConditionOnLocal() throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/trace/queryCondition?callback=jQuery11020021555292898187584_1382520578214&_=1382520578215");
		httpGet.setHeader("key", "value");
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		
		if (null != entity) {
			System.out.println(EntityUtils.toString(entity, "UTF-8"));
			EntityUtils.consume(entity);
		}
	}
	
	@Test
	public void testQueryConditionOnQA() throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://10.100.90.183:8080/trace/queryCondition?callback=jQuery11020021555292898187584_1382520578214&_=1382520578215");
		
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		
		if (null != entity) {
			System.out.println(EntityUtils.toString(entity, "UTF-8"));
			EntityUtils.consume(entity);
		}
	}

	@Test
	public void testQueryConditionOnLine() throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://microscope.vipshop.com:80/trace/queryCondition?callback=jQuery11020021555292898187584_1382520578214&_=1382520578215");
		
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		
		if (null != entity) {
			System.out.println(EntityUtils.toString(entity, "UTF-8"));
			EntityUtils.consume(entity);
		}
	}

	
}
