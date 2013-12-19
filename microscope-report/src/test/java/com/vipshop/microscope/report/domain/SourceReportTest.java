package com.vipshop.microscope.report.domain;

import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.util.IPAddressUtil;
import com.vipshop.microscope.report.factory.MySQLFactory;

public class SourceReportTest {
	
	@Test
	public void testCRUD() {
		MySQLFactory.SOURCE.saveSourceReport(mockSourceReport());
		MySQLFactory.SOURCE.count();
	}
	
	@Test
	public void testsave() {
		MySQLFactory.SOURCE.saveSourceReport(mockSourceReport());
		MySQLFactory.SOURCE.count();
	}
	
	public SourceReport mockSourceReport() {
		SourceReport report = new SourceReport();
		
		report.setYear(2013);
		report.setMonth(11);
		report.setWeek(4);
		report.setDay(1);
		report.setHour(1);
		
		report.setAppName("UserAPI");
		report.setAppIp(IPAddressUtil.intIPAddress("localhost"));
		report.setServerName("db@test");
		report.setSqlType("write");
		
		report.setAvg(1.3f);
		report.setTotalCount(36000);
		report.setFailCount(36000);
		report.setFailPercent(36000);
		report.setStartTime(System.currentTimeMillis());
		report.setEndTime(System.currentTimeMillis() + 1000);
		
		report.setQps(1.2f);
		
		return report;
	}
}
