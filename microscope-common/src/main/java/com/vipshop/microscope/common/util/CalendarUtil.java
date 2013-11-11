package com.vipshop.microscope.common.util;

import java.util.Calendar;

public class CalendarUtil {
	
	private static final Calendar CALENDAR = Calendar.getInstance();
	
	public static int currentYear() {
		return CALENDAR.get(Calendar.YEAR);
	}
	
	public static int currentMonth() {
		return CALENDAR.get(Calendar.MONTH) + 1;
	}
	
	public static int currentWeek() {
		return CALENDAR.get(Calendar.WEEK_OF_MONTH);
	}
	
	public static int currentDay() {
		return CALENDAR.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int currentHour() {
		return CALENDAR.get(Calendar.HOUR_OF_DAY);
	}
	
	@SuppressWarnings("deprecation")
	public static long uniqueTimeStamp() {
		return new java.util.Date(currentYear(), currentMonth(), currentDay(), currentHour(), 0, 0).getTime();
	}
	
	public static long uniquePreTimeStamp() {
		return uniqueTimeStamp() - 1000 * 60 *60;
	}
	
}
