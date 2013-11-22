package com.vipshop.microscope.test.app.action;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.HttpClientUtil;

public class UserControllerTest {
	
	@Test
	public void testfind() throws ClientProtocolException, IOException {
		String url = "http://localhost:9090/user/find?callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
}
