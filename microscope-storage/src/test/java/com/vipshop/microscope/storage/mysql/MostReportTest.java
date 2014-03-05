package com.vipshop.microscope.storage.mysql;

import java.util.List;

import org.testng.annotations.Test;

import com.vipshop.microscope.storage.mysql.condition.MostReportCondition;
import com.vipshop.microscope.storage.mysql.domain.MostReport;
import com.vipshop.microscope.storage.mysql.factory.MySQLFactory;

public class MostReportTest {
	
	@Test
	public void testFindMostReport() {
		MostReportCondition condition = new MostReportCondition();
		condition.setLimit(10);
		condition.setType(3);
		List<MostReport> reports = MySQLFactory.MOST.findMostReport(3);
		System.out.println(reports);
	}
}
