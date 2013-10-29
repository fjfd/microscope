package com.vipshop.microscope.web.service;

import org.testng.annotations.Test;

import com.vipshop.microscope.web.service.TraceService;

public class TraceServiceTest {
	
	TraceService service = new TraceService();
	
	@Test
	public void testGetTraceList() {
		System.out.println(service.getTraceSpan("-4403393697944183921"));;
		System.out.println("skfjdl");
	}
	
	

}
