package com.vipshop.microscope.report.domain;

import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.util.IPAddressUtil;
import com.vipshop.microscope.report.domain.ProblemReport;
import com.vipshop.microscope.report.factory.MySQLFactory;

public class ProblemReportTest {
	
	@Test
	public void testCRUD() {
		MySQLFactory.PROBLEM.saveProblemReport(mockProblemReport());
	}
	
	public ProblemReport mockProblemReport(){
		ProblemReport report = new ProblemReport();
		report.setYear(2013);
		report.setMonth(11);
		report.setWeek(4);
		report.setDay(1);
		report.setHour(1);
		report.setAppName("picket");
		report.setAppIp(IPAddressUtil.intIPAddress("localhost"));
		report.setProType(1);
		report.setProTime(1);
		report.setProCount(1);
		report.setProDesc("example");
		
		return report;
	}
}
