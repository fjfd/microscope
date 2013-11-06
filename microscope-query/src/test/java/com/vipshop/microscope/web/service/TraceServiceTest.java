package com.vipshop.microscope.web.service;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.TimeRangeUtil;
import com.vipshop.microscope.web.service.TraceService;

public class TraceServiceTest {
	
	TraceService service = new TraceService();
	
	@Test
	public void testGetQueryCondition() {
		System.out.println(service.getQueryCondition());;
	}
	
	@Test
	public void testGetTraceList() {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", "picket");
		query.put("traceName", "order");
		query.put("startTime", String.valueOf(TimeRangeUtil.dayOfBegin()));
		query.put("endTime", String.valueOf(TimeRangeUtil.dayOfEnd()));
		query.put("limit", "100");
		System.out.println(service.getTraceList(query));;
	}
	
	@Test
	public void testGetTraceSpan() {
		System.out.println(service.getTraceSpan("-9113400607919331149"));
	}
	

}
