package com.vipshop.microscope.report.domain;

import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.domain.DepenReport;

public class DepenReportTest {
	
	@Test
	public void testCRUD() {
		mockDepenReport();
	}
	
	public void mockDepenReport() {
		DepenReport report = new DepenReport();
		
		report.setClientName("tets");
		report.setServerName("server");
		report.setDateByHour(new CalendarUtil());
		report.setTotalCount(1);
		report.setFailCount(1);
		report.setFailPercent(1);
		report.setAvg(1);
		report.setQps(1);
		report.setStartTime(System.currentTimeMillis());
		report.setEndTime(System.currentTimeMillis() + 1000);
		
		report.saveReport();
	}
}

