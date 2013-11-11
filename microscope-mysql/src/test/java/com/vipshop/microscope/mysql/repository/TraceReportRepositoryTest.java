package com.vipshop.microscope.mysql.repository;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.domain.TraceReport;

public class TraceReportRepositoryTest {
	
	TraceReportRepository mySQLTemplate = MySQLRepositorys.TRACE_REPORT;
	
	@Test
	public void testCreate() {
		String sql = "CREATE TABLE t1(id int not null,name char(20))";
		mySQLTemplate.create(sql);
	}
	
	@Test
	public void testSave() {
		TraceReport traceReport = new TraceReport();
		traceReport.setId(TraceReport.makeId("example"));
		traceReport.setYear(CalendarUtil.currentYear());
		traceReport.setMonth(CalendarUtil.currentMonth());
		traceReport.setWeek(CalendarUtil.currentWeek());
		traceReport.setDay(CalendarUtil.currentDay());
		traceReport.setHour(CalendarUtil.currentHour());
		traceReport.setType("action");
		traceReport.setName("example");
		traceReport.setTotalCount(100);
		traceReport.setFailureCount(10);
		traceReport.setFailurePrecent(1.2f);
		traceReport.setMin(4.2f);
		traceReport.setMax(4234f);
		traceReport.setAvg(232);
		traceReport.setTps(1.0f);
		mySQLTemplate.save(traceReport);
	}
	
	@Test
	public void testUpdate() {
		TraceReport traceStat = new TraceReport();
		traceStat.setType("trace/queryconditon");
		traceStat.setTotalCount(1000);
		traceStat.setFailureCount(10);
		traceStat.setFailurePrecent(1.2f);
		traceStat.setMin(4.2f);
		traceStat.setMax(4234f);
		traceStat.setAvg(232);
		
		mySQLTemplate.update(traceStat);
	}
	
	@Test
	public void testfind() {
		System.out.println(mySQLTemplate.findTraceReport());;
	}
	
	@Test
	public void exist() {
		System.out.println(mySQLTemplate.exist("trace/queryconditon"));
	}
}
