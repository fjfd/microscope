package com.vipshop.microscope.collector.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.factory.MySQLRepositorys;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.thrift.Span;

public class TraceAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceAnalyzer.class);
	
	private final ConcurrentHashMap<String, TraceReport> container = new ConcurrentHashMap<String, TraceReport>();
	
	public void analyze(Span span, CalendarUtil calendarUtil) {
		synchronized (container) {
			String key = TraceReport.makePreId(calendarUtil, span.getType());
			
			TraceReport report = container.get(key);
			
			if (report != null) {
				try {
					logger.info("save report to mysql " + report);
					MySQLRepositorys.TRACE_REPORT.save(report);
				} catch (Exception e) {
					logger.error("lost report to mysql " + report);
				} finally {
					container.remove(key);
				}
			}
		}
		
		String type = span.getType();
		String name = span.getName();
		String resultCode = span.getResultCode();
		int duration = span.getDuration() / 1000;
		long startTime = span.getStartstamp();
		long endTime = span.getStartstamp() + duration;
		
		String key = TraceReport.makeId(calendarUtil, type);
		TraceReport report = container.get(key);
		// first time 
		if (report == null) {
			
			report = new TraceReport();
			
			report.setId(key);
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(calendarUtil.currentHour());
			report.setType(type);
			report.setName(name);
			
			report.setTotalCount(1);
			if (resultCode.equals("OK")) {
				report.setFailureCount(0);
				report.setFailurePrecent(0/1);
			} else {
				report.setFailureCount(1);
				report.setFailurePrecent(1/1);
			}
			
			report.setMin(duration);
			report.setMax(duration);
			report.setAvg(duration);
			
			report.setSum(duration);
			report.setStartTime(startTime);
			report.setEndTime(endTime);
			
		} else {
			
			report.setTotalCount(report.getTotalCount() + 1);
			if (!resultCode.equals("OK")) {
				report.setFailureCount(report.getFailureCount() + 1);
			} 
			float precent = report.getFailureCount() / report.getTotalCount();
			report.setFailurePrecent(precent);
			
			if (duration < report.getMin()) {
				report.setMin(duration);
			}
			
			if (duration > report.getMax()) {
				report.setMax(duration);
			}
			
			report.setSum(report.getSum() + duration);
			report.setAvg(report.getSum() / report.getTotalCount());
			
			if (startTime < report.getStartTime()) {
				report.setStartTime(startTime);
			}
			
			if (endTime > report.getEndTime()) {
				report.setEndTime(endTime);
			}
			logger.info("report is " + report);
			
		}
		
		report.setDuration(report.getEndTime() - report.getStartTime());
		report.setTps(TraceReport.makeTPS(report));

		container.put(key, report);
		
	}
}
