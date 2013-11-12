package com.vipshop.microscope.web.query;

import org.testng.annotations.Test;

public class MySQLQueryTemplateTest {
	
	MySQLQueryTemplate template = new MySQLQueryTemplate();
	
	@Test
	public void testBeforeHourTraceReport() {
		System.out.println(template.beforeHourTraceReport(1));;
	}
}
