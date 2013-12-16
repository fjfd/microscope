package com.vipshop.microscope.report.repository;

import org.testng.annotations.Test;

import com.vipshop.microscope.report.factory.MySQLFactory;

public class MySQLRepositoryTest {

	@Test
	public void testEmpty() {
		MySQLFactory.TOP.empty();
		MySQLFactory.TRACE.empty();
		MySQLFactory.TRACE.emptyOverTime();
		MySQLFactory.SOURCE.empty();
		MySQLFactory.DEPEN.empty();
		MySQLFactory.PROBLEM.empty();
		MySQLFactory.MSG.empty();
	}
	
}
