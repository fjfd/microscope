package com.vipshop.microscope.common.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
	
	public static long currentTimeMicros() {
		return TimeUnit.MILLISECONDS.toMicros(System.currentTimeMillis());
	}
	
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
}
