package com.vipshop.microscope.report.domain;

import java.util.List;

import org.testng.annotations.Test;

import com.vipshop.microscope.report.condition.MostReportCondition;
import com.vipshop.microscope.report.factory.MySQLFactory;

public class MostReportTest {
	
	@Test
	public void testFindMostReport() {
		MostReportCondition condition = new MostReportCondition();
		condition.setLimit(10);
		condition.setType(3);
		List<MostReport> reports = MySQLFactory.MOST.findMostReport(condition);
		System.out.println(reports);
	}
}
