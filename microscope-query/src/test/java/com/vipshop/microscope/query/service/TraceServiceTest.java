package com.vipshop.microscope.query.service;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

public class TraceServiceTest {
	
	TraceSerivice service = new TraceSerivice();
	
	@Test
	public void testGetQueryCondition() {
	}
	
	@Test
	public void testGetTraceList() {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", "picket");
		query.put("traceName", "order");
		query.put("limit", "100");
	}
	
	@Test
	public void testGetTraceSpan() {
	}
	

}
