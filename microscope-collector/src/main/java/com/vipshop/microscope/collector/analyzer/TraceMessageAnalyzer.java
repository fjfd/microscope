package com.vipshop.microscope.collector.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.collector.report.ReportComputer;
import com.vipshop.microscope.collector.report.ReportContainer;
import com.vipshop.microscope.collector.report.ReportFrequency;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.report.DurationDistReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageAnalyzer {
	
	private static final ConcurrentHashMap<Long, TraceReport> traceContainer = ReportContainer.getTracecontainer();
	private static final ConcurrentHashMap<Long, DurationDistReport> duraDistContainer = ReportContainer.getDuradistcontainer();
	private static final ConcurrentHashMap<Long, OverTimeReport> overTimeContainer = ReportContainer.getOvertimecontainer();
	
	public void analyze(Span span) {
		
		CalendarUtil calendarUtil = new CalendarUtil();
		
		String app = span.getApp_name();
		String ipAdress = span.getIPAddress();
		String type = span.getType();
		String name = span.getName();
		
		long keyHour = ReportFrequency.generateKeyByHour(calendarUtil);
		processTrace(span, calendarUtil, app, ipAdress, type, name, keyHour);
		processDuraDist(span, calendarUtil, app, ipAdress, type, name, keyHour);
		
		long key5Minute = ReportFrequency.generateKeyBy5Minute(calendarUtil);
		processOverTime(span, calendarUtil, app, ipAdress, type, name, key5Minute);
		
	}

	private void processTrace(Span span, CalendarUtil calendarUtil, String app, String ipAdress, String type, String name, long key) {
		
		String resultCode = span.getResultCode();
		int duration = span.getDuration() / 1000;
		long startTime = span.getStartstamp();
		long endTime = span.getStartstamp() + duration;
		
		TraceReport report = traceContainer.get(key);
		// first time 
		if (report == null) {
			
			report = new TraceReport();
			
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(calendarUtil.currentHour());
			report.setApp(app);
			report.setIpAdress(name);
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
			
		}
		
		report.setTps(TraceReport.makeTPS(report));

		traceContainer.put(key, report);
	}
	
	private void processDuraDist(Span span, CalendarUtil calendarUtil, String app, String ipAdress, String type, String name, long key) {
		DurationDistReport durationDistReport = duraDistContainer.get(key);
		
		if (durationDistReport == null) {
			durationDistReport = new DurationDistReport();
			durationDistReport.setYear(calendarUtil.currentYear());
			durationDistReport.setMonth(calendarUtil.currentMonth());
			durationDistReport.setWeek(calendarUtil.currentWeek());
			durationDistReport.setDay(calendarUtil.currentDay());
			durationDistReport.setHour(calendarUtil.currentHour());
			durationDistReport.setApp(app);
			durationDistReport.setIpAdress(ipAdress);
			durationDistReport.setType(type);
			durationDistReport.setName(name);
		} 
		
		int dura = span.getDuration();
		durationDistReport.updateRegion(ReportComputer.log2(dura));
		
		duraDistContainer.put(key, durationDistReport);
	}
	
	private void processOverTime(Span span, CalendarUtil calendarUtil, String app, String ipAdress, String type, String name, long key5Minute) {
		OverTimeReport report = overTimeContainer.get(key5Minute);
		if (report == null) {
			report = new OverTimeReport();
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(calendarUtil.currentHour());
			report.setMinute((calendarUtil.currentMinute() / 5) * 5);
			report.setApp(app);
			report.setIpAdress(ipAdress);
			report.setType(type);
			report.setName(name);
			report.setAvgDura(span.getDuration());
			report.setHitCount(1);
			
		} else {
			report.setHitCount(report.getHitCount() + 1);
			report.setAvgDura((report.getAvgDura() + span.getDuration()) / report.getHitCount());
		}
		
		if (!span.getResultCode().equals("OK")) {
			report.setFailCount(report.getFailCount() + 1);
		}
		
		overTimeContainer.put(key5Minute, report);
	}

}
