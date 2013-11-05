package com.vipshop.microscope.job.mapreduce;

/**
 *项目名：microscope-job
 *包名：com.vipshop.microscope.hbase.mapreduce.comm
 *创建日期：2013-10-28
 *作者：malaka.weng@vipshop.com
 */

import java.util.Calendar;

public class DateUtil {
	public static long[] getTime(int range) {
		long[] arrtim = new long[2];
		Calendar cal = Calendar.getInstance();
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		System.out.println(cal.getTime());
		arrtim[1] = cal.getTimeInMillis();
		cal.add(5, -range);
		System.out.println(cal.getTime());
		arrtim[0] = cal.getTimeInMillis();
		System.out.println("" + arrtim[0] + "   " + arrtim[1]);
		return arrtim;
	}

	public static void main(String[] args) {
		getTime(7);
	}
}