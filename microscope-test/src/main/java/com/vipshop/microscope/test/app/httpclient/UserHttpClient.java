package com.vipshop.microscope.test.app.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class UserHttpClient {
	
	public void insertRequest() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/insert?callback=jQuery11020021555292898187584";
		request(url);
	}
	
	public void findRequest() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/find?callback=jQuery11020021555292898187584";
		request(url);
	}
	
	public void updateRequest() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/update?callback=jQuery11020021555292898187584";
		request(url);
	}

	public void deleteRequest() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/delete?callback=jQuery11020021555292898187584";
		request(url);
	}
	
	public static String request(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		
		if (null != entity) {
			return EntityUtils.toString(entity, "UTF-8");
		}
		return null;
	}

}
