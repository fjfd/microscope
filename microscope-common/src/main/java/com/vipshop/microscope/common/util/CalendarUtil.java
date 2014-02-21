package com.vipshop.microscope.common.util;

import java.util.Calendar;

public class CalendarUtil {
	
	private final Calendar CALENDAR = Calendar.getInstance();
	
	public int currentYear() {
		return CALENDAR.get(Calendar.YEAR);
	}
	
	public int currentMonth() {
		return CALENDAR.get(Calendar.MONTH) + 1;
	}
	
	public int currentWeek() {
		return CALENDAR.get(Calendar.WEEK_OF_MONTH);
	}
	
	public int currentDay() {
		return CALENDAR.get(Calendar.DAY_OF_MONTH);
	}
	
	public int currentHour() {
		return CALENDAR.get(Calendar.HOUR_OF_DAY);
	}
	
	public int currentMinute() {
		return CALENDAR.get(Calendar.MINUTE);
	}
	
	public int currentMills() {
		return CALENDAR.get(Calendar.MILLISECOND);
	}
}
