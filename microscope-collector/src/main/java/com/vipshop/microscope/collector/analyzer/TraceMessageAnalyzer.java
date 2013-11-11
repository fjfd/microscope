package com.vipshop.microscope.collector.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.domain.TraceReport;
import com.vipshop.microscope.mysql.repository.MySQLRepositorys;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageAnalyzer {
	
	private static final ConcurrentHashMap<String, TraceReport> container = new ConcurrentHashMap<String, TraceReport>();
	
	public void analyze(Span span) {
		
		String name = span.getName();

		checkPrevious(name);
		
		String type = span.getType();
		String resultCode = span.getResultCode();
		int duration = span.getDuration();
		long startTime = span.getStartstamp();
		long endTime = span.getStartstamp() + duration;
		
		String key = TraceReport.makeId(name);
		TraceReport report = container.get(key);
		// first time 
		if (report == null) {
			
			report = new TraceReport();
			
			report.setId(key);
			report.setYear(CalendarUtil.currentYear());
			report.setMonth(CalendarUtil.currentMonth());
			report.setWeek(CalendarUtil.currentWeek());
			report.setDay(CalendarUtil.currentDay());
			report.setHour(CalendarUtil.currentHour());
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
			
			report.setDuration(report.getEndTime() - report.getStartTime());
		}

		container.put(key, report);
	
	}
	
	private void checkPrevious(String name) {
		String key = TraceReport.makePreId(name);
		TraceReport report = container.get(key);
		if (report != null) {
			try {
				report.setTps(TraceReport.makeTPS(report));
				MySQLRepositorys.TRACE_REPORT.save(report);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				container.remove(key);
			}
		}
	}
}
