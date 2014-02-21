package com.vipshop.microscope.report.domain;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.span.Category;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.report.condition.TraceReportCondition;
import com.vipshop.microscope.report.factory.MySQLFactory;

public class TraceReportTest {
	
	@Test
	public void testsave() {
//		MySQLFactory.TRACE.empty();
		List<TraceReport> reports = mockTraceReports();
		for (TraceReport traceReport : reports) {
			traceReport.saveReport();
		}
		
		Assert.assertEquals(24, MySQLFactory.TRACE.countTraceReport());
	}
	
	@Test
	public void testTraceReport() {

		TraceReport report = mockTraceReport();
		report.saveReport();
		Assert.assertEquals(1, MySQLFactory.TRACE.countTraceReport());
		
		TraceReportCondition condition = new TraceReportCondition();
		condition.setAppName("picket");
		Assert.assertEquals(1, MySQLFactory.TRACE.findTraceReport(condition).size());
		
		TraceReport report1 = MySQLFactory.TRACE.findTraceDuration(condition);
		Assert.assertEquals(100, report1.getRegion_0());
		Assert.assertEquals(1, report1.getRegion_1());
		Assert.assertEquals(1, MySQLFactory.TRACE.findAppName().size());
		Assert.assertEquals(1, MySQLFactory.TRACE.findIPAdress("picket").size());
		
		MySQLFactory.TRACE.empty();
	}
	
	@Test
	public void testTraceOverTimeReport() {
		MySQLFactory.TRACE.emptyOverTime();
		MySQLFactory.TRACE.saveOverTimeReport(mockTraceOverTimeReport());
		
		Assert.assertEquals(1, MySQLFactory.TRACE.countTraceOverTimeReport());
		
		TraceReportCondition condition = new TraceReportCondition();
		condition.setAppName("picket");
		Assert.assertEquals(1, MySQLFactory.TRACE.findOverTimeReport(condition).size());
	}
	
	@Test
	public void testfindTraceDuration() {
		TraceReportCondition condition = new TraceReportCondition();
		condition.setAppName("picket");
		condition.setGroupBy("type");
		condition.setDay(9);
		condition.setHour(12);
		condition.setMonth(12);
		condition.setWeek(2);
		condition.setType(Category.getIntValue("URL"));
		System.out.println(MySQLFactory.TRACE.findTraceDuration(condition));;
	}
	
	private TraceReport mockTraceReport() {
		TraceReport report = new TraceReport();
		report.setYear(2013);
		report.setMonth(11);
		report.setWeek(3);
		report.setDay(1);
		report.setHour(1);
		report.setAppName("passport");
		report.setAppIp(12324312);
		report.setType(Category.getIntValue("Action"));
		report.setName("query:select * from trace_report");
		report.setTotalCount(100);
		report.setFailCount(10);
		report.setFailPercent(0.1f);
		report.setMin(100);
		report.setMax(1000);
		report.setAvg(200);
		report.setQps(1.3f);
		report.setStartTime(System.currentTimeMillis());
		report.setEndTime(System.currentTimeMillis() + 1000);
		report.setRegion_0(100);
		report.setRegion_1(1);
		report.setRegion_2(1);
		report.setRegion_3(1);
		report.setRegion_4(1);
		report.setRegion_5(1);
		report.setRegion_6(1);
		report.setRegion_7(1);
		report.setRegion_8(1);
		report.setRegion_9(1);
		report.setRegion_10(1);
		report.setRegion_11(1);
		report.setRegion_12(1);
		report.setRegion_13(1);
		report.setRegion_14(1);
		report.setRegion_15(1);
		report.setRegion_16(1);
		
		return report;
	}
	
	private List<TraceReport> mockTraceReports() {
		List<TraceReport> reports = new ArrayList<TraceReport>();
		CalendarUtil calendarUtil = new CalendarUtil();
		for (int i = 0; i < 24; i++) {
			TraceReport report = new TraceReport();
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(i);
			report.setAppName("userinfo");
			report.setAppIp(IPAddressUtil.intIPAddress("localhost"));
			report.setType(Category.getIntValue("URL"));
			report.setName("query:select * from trace_report");
			report.setTotalCount(100);
			report.setFailCount(10);
			report.setFailPercent(0.1f);
			report.setMin(100);
			report.setMax(1000);
			report.setAvg(200);
			report.setQps(1.3f);
			report.setStartTime(System.currentTimeMillis());
			report.setEndTime(System.currentTimeMillis() + 1000);
			report.setRegion_0(100);
			report.setRegion_1(1);
			report.setRegion_2(1);
			report.setRegion_3(1);
			report.setRegion_4(1);
			report.setRegion_5(1);
			report.setRegion_6(1);
			report.setRegion_7(1);
			report.setRegion_8(1);
			report.setRegion_9(1);
			report.setRegion_10(1);
			report.setRegion_11(1);
			report.setRegion_12(1);
			report.setRegion_13(1);
			report.setRegion_14(1);
			report.setRegion_15(1);
			report.setRegion_16(1);
			
			reports.add(report);
		}
		
		return reports;
	}
	
	public TraceOverTimeReport mockTraceOverTimeReport(){
		TraceOverTimeReport report = new TraceOverTimeReport();
		report.setYear(2013);
		report.setMonth(11);
		report.setWeek(3);
		report.setDay(1);
		report.setHour(1);
		report.setMinute(2);
		report.setAppName("picket");
		report.setAppIp(IPAddressUtil.intIPAddress("localhost"));
		report.setType(Category.getIntValue("DB"));
		report.setName("query:select * from trace_report");
		report.setAvg(100f);
		report.setHit(1000);
		report.setFail(10);
		
		return report;
	}
}
