package com.vipshop.microscope.mysql.domain;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.Logarithm;
import com.vipshop.microscope.mysql.report.DurationDistReport;

public class DurationDistReportTest {
	
	@Test
	public void testUpdateRegion() {
		DurationDistReport report = new DurationDistReport();
		
		
		report.updateRegion(45);
		report.updateRegion(78);
		report.updateRegion(45);
		report.updateRegion(12);
		report.updateRegion(1);
		report.updateRegion(4);
		
		System.out.println(Logarithm.log(4, 2));
		System.out.println(report);
	}
}
