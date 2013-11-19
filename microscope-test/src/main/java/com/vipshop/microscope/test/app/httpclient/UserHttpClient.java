package com.vipshop.microscope.test.app.httpclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.vipshop.microscope.common.util.HttpClientUtil;

public class UserHttpClient {
	
	public void insertRequest() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/insert?callback=jQuery11020021555292898187584";
		HttpClientUtil.request(url);
	}
	
	public void findRequest() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/find?callback=jQuery11020021555292898187584";
		HttpClientUtil.request(url);
	}
	
	public void updateRequest() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/update?callback=jQuery11020021555292898187584";
		HttpClientUtil.request(url);
	}

	public void deleteRequest() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/delete?callback=jQuery11020021555292898187584";
		HttpClientUtil.request(url);
	}

}
