package com.vipshop.microscope.web.action;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.HttpClientUtil;

public class MsgReportTest {
	
	@Test
	public void testGetMsgReport() throws ClientProtocolException, IOException {
		String url = "http://localhost:8080/report/msgReport?year=2013&month=11&week=4&day=17&callback=jQuery11020021555292898187584";
		String result = HttpClientUtil.request(url);
		System.out.println(result);
	}
}
