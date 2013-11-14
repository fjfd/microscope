package com.vipshop.microscope.collector.report;

import com.vipshop.microscope.common.util.CalendarUtil;


public class ReportFrequency {
	
	@SuppressWarnings("deprecation")
	public static long generateKeyByHour(CalendarUtil calendar) {
		
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      0, 
							      0).getTime();
	}
	
	public static long getPreKeyByHour(CalendarUtil calendar) {
		long key = generateKeyByHour(calendar);
		long preKey = key - (1000 * 60 *60);
		return preKey;
	}
	
	@SuppressWarnings("deprecation")
	public static long generateKeyBy5Minute(CalendarUtil calendar) {
		
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      (calendar.currentMinute() / 5) * 5, 
							      0).getTime();
	}
	
	public static long getPreKeyBy5Minute(CalendarUtil calendar) {
		return (generateKeyBy5Minute(calendar) - (1000 * 60 * 5));
	}

	
	@SuppressWarnings("deprecation")
	public static long generateKeyByMinute(CalendarUtil calendar) {
		
		return new java.util.Date(calendar.currentYear(), 
							      calendar.currentMonth(), 
							      calendar.currentDay(), 
							      calendar.currentHour(), 
							      calendar.currentMinute(), 
							      0).getTime();
	}
	
	public static long getPreKeyByMinute(CalendarUtil calendar) {
		return (generateKeyByMinute(calendar) - (1000 * 60));
	}
	
	

	
}
