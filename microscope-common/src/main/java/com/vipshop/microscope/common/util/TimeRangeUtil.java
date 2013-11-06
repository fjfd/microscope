package com.vipshop.microscope.common.util;

import java.util.Calendar;

public class TimeRangeUtil {
	
	public static long now() {
		return Calendar.getInstance().getTimeInMillis();
	}
	
	public static long dayOfBegin() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	public static long dayOfEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}
}
