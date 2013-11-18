package com.vipshop.microscope.mysql.repository;

import org.testng.annotations.Test;

import com.vipshop.microscope.mysql.condition.SourceReportCondition;
import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public class ReportRepositoryTest {
	
	ReportRepository reportRepository = new ReportRepository();
	
	@Test
	public void testEmpty() {
		reportRepository.emptyTraceReport();
	}
	
	@Test
	public void testSaveTraceReport() {
		for (int i = 0; i < 1; i++) {
			TraceReport report = new TraceReport();
			report.setYear(2013);
			report.setMonth(11);
			report.setWeek(3);
			report.setDay(14);
			report.setHour(13);
			report.setApp("picket");
			report.setIpAdress("localhost");
			report.setType("DB");
			report.setName("query:select * from trace_report");
			report.setTotalCount(100);
			report.setFailureCount(10);
			report.setFailurePrecent(0.1f);
			report.setMin(100);
			report.setMax(1000);
			report.setAvg(200);
			report.setTps(1.3f);
			report.setStartTime(System.currentTimeMillis());
			report.setEndTime(System.currentTimeMillis() + 1000);
			report.setRegion_0(1);
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
			reportRepository.save(report);
		}
	}
	
	@Test
	public void testFind() {
		TraceReportCondition condition = new TraceReportCondition();
		condition.setAppName("picket");
		condition.setType("Cache");
		condition.setYear(2013);
		condition.setMonth(11);
		condition.setDay(15);
		condition.setHour(11);
		condition.setName("%cache%");
		condition.setGroupBy("none");
		System.out.println(reportRepository.findTraceReport(condition));;
	}
	
	@Test
	public void testSaveOverTimeReport() {
		for (int i = 0; i < 100; i++) {
			OverTimeReport report = new OverTimeReport();
			report.setYear(2013);
			report.setMonth(11);
			report.setWeek(3);
			report.setDay(14);
			report.setHour(13);
			report.setApp("picket");
			report.setIpAdress("localhost");
			report.setType("DB");
			report.setName("query:select * from trace_report");
			report.setAvgDura(100f);
			report.setHitCount(1000);
			report.setFailCount(10);
			
			reportRepository.save(report);
		}
	}
	
	@Test
	public void emptyOverTimeReport() {
		reportRepository.emptyOverTimeReport();
	}
	
	@Test
	public void saveMsgReport() {
		for (int i = 0; i < 100; i++) {
			MsgReport report = new MsgReport();
			
			report.setYear(2013);
			report.setMonth(11);
			report.setWeek(3);
			report.setDay(14);
			report.setHour(13);
			
			report.setMsgSize(1000);
			report.setMsgNum(1000);
			
			reportRepository.save(report);
			
		}
	}
	
	@Test
	public void saveSourceReport() {
		SourceReport report = new SourceReport();
		
		report.setYear(2013);
		report.setMonth(11);
		report.setWeek(3);
		report.setDay(14);
		report.setHour(13);
		
		report.setApp("picket");
		report.setName("/login/db");
		report.setServiceIPAdress("10.101.0.200");
		report.setServiceName("db@feel");
		report.setServiceType("DB");
		
		report.setAvgDura(1.3f);
		report.setCount(1);
		report.setStartTime(System.currentTimeMillis());
		report.setEndTime(System.currentTimeMillis() + 1000);
		
		report.setTps(1.2f);
		
		reportRepository.save(report);
	}
	
	@Test
	public void testfindSourceReport() {
		SourceReportCondition condition = new SourceReportCondition();
		
		condition.setServiceIPAdress("10.101.0.200");
		condition.setServiceType("DB");
		
		System.out.println(reportRepository.findSourceReport(condition));;
	}
	
	@Test
	public void testfindSourceReportDist() {
		SourceReportCondition condition = new SourceReportCondition();
		
		condition.setServiceIPAdress("10.101.0.200");
		condition.setServiceType("DB");
		System.out.println(reportRepository.findSourceReportDist(condition));;
	}
	
	@Test
	public void testfindSourceReportTOP() {
		SourceReportCondition condition = new SourceReportCondition();
		
		condition.setServiceIPAdress("10.101.0.200");
		condition.setServiceType("DB");
		System.out.println(reportRepository.findSourceReportTOP(condition));;
	}
}
