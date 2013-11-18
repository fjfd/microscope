package com.vipshop.microscope.collector.report;

import com.vipshop.microscope.common.util.CalendarUtil;


public class ReportFrequency {
	
	@SuppressWarnings("deprecation")
	public static long makeKeyByHour(CalendarUtil calendar) {
		
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      0, 
							      0).getTime();
	}
	
	public static long getPreKeyByHour(CalendarUtil calendar) {
		return makeKeyByHour(calendar) - (1000 * 60 *60);
	}
	
	public static String makeKeyByHour(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(makeKeyByHour(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		
		return builder.toString();
	}
	
	public static String getPreKeyByHour(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		long key = makeKeyByHour(calendar);
		long preKey = key - (1000 * 60 *60);
		StringBuilder builder = new StringBuilder();
		builder.append(preKey)
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		
		return builder.toString();
	}
	
	public static String makeKeyByHour(CalendarUtil calendar, String app, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(makeKeyByHour(calendar))
			   .append("-").append(app)
			   .append("-").append(name);
		
		return builder.toString();
	}
	
	public static String getPreKeyByHour(CalendarUtil calendar, String app, String name) {
		long key = makeKeyByHour(calendar);
		long preKey = key - (1000 * 60 *60);
		StringBuilder builder = new StringBuilder();
		builder.append(preKey)
			   .append("-").append(app)
			   .append("-").append(name);
		
		return builder.toString();
	}

	
	@SuppressWarnings("deprecation")
	public static long makeKeyBy5Minute(CalendarUtil calendar) {
		
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      (calendar.currentMinute() / 5) * 5, 
							      0).getTime();
	}
	
	public static String makeKeyBy5Minute(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(makeKeyBy5Minute(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		
		return builder.toString();
	}
	
	public static String getPreKeyBy5Minute(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(makeKeyBy5Minute(calendar) - (1000 * 60 * 5))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		
		return builder.toString();
	}

	
	@SuppressWarnings("deprecation")
	public static long makeKeyByMinute(CalendarUtil calendar) {
		
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      calendar.currentMinute(), 
							      0).getTime();
	}
	
	public static String makeKeyByMinute(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(makeKeyByMinute(calendar) - (1000 * 60 * 5))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		
		return builder.toString();
	}
	
	public static String getPreKeyByMinute(CalendarUtil calendar, String app, String ipAdress, String type, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append(makeKeyByMinute(calendar) - (1000 * 60))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		
		return builder.toString();
	}
	
	

	
}
