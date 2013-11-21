package com.vipshop.microscope.collector.analyzer.report;

import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public class ReportContainer {
	
	private static final ConcurrentHashMap<Long, MsgReport> msgContainer = new ConcurrentHashMap<Long, MsgReport>();
	private static final ConcurrentHashMap<String, TraceReport> traceContainer = new ConcurrentHashMap<String, TraceReport>();
	private static final ConcurrentHashMap<String, OverTimeReport> overTimeContainer = new ConcurrentHashMap<String, OverTimeReport>();
	private static final ConcurrentHashMap<String, SourceReport> sourceReportContainer = new ConcurrentHashMap<String, SourceReport>();

	public static long getKeyOfMsgReport(CalendarUtil calendar) {
		return timestampOfCurrentHour(calendar);
	}

	public static long getPreKeyOfMsgReport(CalendarUtil calendar) {
		return timestampOfPrevHour(calendar);
	}
	
	public static MsgReport getMsgReport(long key) {
		return msgContainer.get(key);
	}
	
	public static void removeMsgReport(long key) {
		msgContainer.remove(key);
	}
	
	public static void put(long key, MsgReport msgReport) {
		msgContainer.put(key, msgReport);
	}
	
	public static String getKeyOfTraceReport(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(timestampOfCurrentHour(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		return builder.toString();
	}
	
	public static String getPreKeyOfTraceReport(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(timestampOfPrevHour(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		return builder.toString();
	}
	
	public static TraceReport getTraceReport(String key) {
		return traceContainer.get(key);
	}
	
	public static void removeTraceReport(String key) {
		traceContainer.remove(key);
	}
	
	public static void put(String key, TraceReport traceReport) {
		traceContainer.put(key, traceReport);
	}
	
	public static String getKeyOfOverTimeReport(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(timestampOfCurrent5Minute(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		return builder.toString();
	}
	
	public static String getPreKeyOfOverTimeReport(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(timestampOfPrev5Minute(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		return builder.toString();
	}
	
	public static OverTimeReport getOverTimeReport(String key) {
		return overTimeContainer.get(key);
	}
	
	public static void removeOverTimeReport(String key) {
		overTimeContainer.remove(key);
	}
	
	public static void put(String key, OverTimeReport report) {
		overTimeContainer.put(key, report);
	}
	
	public static String getKeyOfDBResource(CalendarUtil calendar, String app, String ServerIP, String sqlType) {
		StringBuilder builder = new StringBuilder();
		builder.append(timestampOfCurrentHour(calendar))
			   .append("-").append(app)
			   .append("-").append("DB")
		   	   .append("-").append(ServerIP)
		   	   .append("-").append(sqlType);
		return builder.toString();
	}
	
	public static String getPreKeyOfDBSourceReport(CalendarUtil calendar, String app, String ServerIP, String sqlType) {
		StringBuilder builder = new StringBuilder();
		builder.append(timestampOfPrevHour(calendar))
			   .append("-").append(app)
			   .append("-").append("DB")
		   	   .append("-").append(ServerIP)
		   	   .append("-").append(sqlType);
		return builder.toString();
	}
	
	public static SourceReport getSourceReport(String key) {
		return sourceReportContainer.get(key);
	}
	
	public static void removeSourceReport(String key) {
		sourceReportContainer.remove(key);
	}
	
	public static void put(String key, SourceReport report) {
		sourceReportContainer.put(key, report);
	}
	
	@SuppressWarnings("deprecation")
	private static long timestampOfCurrentHour(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      0, 
							      0).getTime();
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfPrevHour(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      0, 
							      0).getTime() - (1000 * 60 *60);
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfCurrent5Minute(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      (calendar.currentMinute() / 5) * 5, 
							      0).getTime();
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfPrev5Minute(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      (calendar.currentMinute() / 5) * 5, 
							      0).getTime() - (1000 * 60 * 5);
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfCurrentMinute(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      calendar.currentMinute(), 
							      0).getTime();
	}
	
	@SuppressWarnings("deprecation")
	public static long timestampOfPrevMinute(CalendarUtil calendar) {
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      calendar.currentMinute(), 
							      0).getTime() - (1000 * 60);
	}


}
