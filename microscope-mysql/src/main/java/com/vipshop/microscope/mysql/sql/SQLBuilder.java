package com.vipshop.microscope.mysql.sql;

import com.vipshop.microscope.common.util.CalendarUtil;


public class SQLBuilder {
	
	public static String beforeHourTraceQuery(int before) { 
		CalendarUtil calendarUtil = new CalendarUtil();
		return "SELECT * FROM trace_report WHERE YEAR = " + calendarUtil.currentYear() 
				                        + " AND MONTH = " + calendarUtil.currentMonth()
				                        + " AND DAY= " + calendarUtil.currentDay() 
				                        + " AND HOUR = " + (calendarUtil.currentHour() - before);
	}
	
	public static String getTraceReportByType() {
		return "SELECT TYPE, NAME, SUM(total_count) AS total_count, SUM(failure_count) AS failure_count, failure_count/total_count AS failure_precent, " +
				"MIN(MIN) AS MIN, MAX(MAX) AS MAX, AVG(AVG) AS AVG, total_count/(MAX(end_time) - MIN(start_time)) AS tps " + 
				"FROM trace_report GROUP BY TYPE ORDER BY total_count";
	}

	public static String getTraceReportByName() {
		return "SELECT TYPE, NAME, SUM(total_count) AS total_count, SUM(failure_count) AS failure_count, failure_count/total_count AS failure_precent, " +
			   "MIN(MIN) AS MIN, MAX(MAX) AS MAX, AVG(AVG) AS AVG, total_count/(MAX(end_time) - MIN(start_time)) AS tps " + 
			   "FROM trace_report GROUP BY NAME ORDER BY total_count LIMIT 100";
	}
	
	public static String getTraceReportByName(String name) {
		return "SELECT TYPE, NAME, SUM(total_count) AS total_count, SUM(failure_count) AS failure_count, failure_count/total_count AS failure_precent, " +
				   "MIN(MIN) AS MIN, MAX(MAX) AS MAX, AVG(AVG) AS AVG, total_count/(MAX(end_time) - MIN(start_time)) AS tps " + 
				   "FROM trace_report WHERE NAME LIKE '%" + name + "%' GROUP BY NAME ORDER BY total_count";
	}
	
	public static String getTraceReportByNameAndTime(String hour) {
		return "SELECT TYPE, NAME, SUM(total_count) AS total_count, SUM(failure_count) AS failure_count, failure_count/total_count AS failure_precent, " +
				   "MIN(MIN) AS MIN, MAX(MAX) AS MAX, AVG(AVG) AS AVG, total_count/(MAX(end_time) - MIN(start_time)) AS tps " + 
				   "FROM trace_report WHERE hour = " + hour + " GROUP BY NAME ORDER BY total_count";
	}
	
	
	
}
