package com.vipshop.microscope.report.domain;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.CalendarUtil;

/**
 * Abstrace report common date.
 * 
 * 1) year
 * 2) month
 * 3) week
 * 4) day
 * 5) hour
 * 6) minute
 * 
 * Report aggregate rule:
 * 
 * Year  report can aggregate by month;
 * Month report can aggregate by week or day;
 * Week  report can aggregate by day
 * Day   report can aggregate by hour;
 * 
 * @author Xu Fei
 * @version 1.0
 */
public abstract class AbstraceReport {
	
	/**
	 * current year int value
	 */
	protected int year;
	
	/**
	 * current month int value
	 */
	protected int month;
	
	/**
	 * current week int value
	 */
	protected int week;
	
	/**
	 * current day int value
	 */
	protected int day;
	
	/**
	 * current hour int value
	 */
	protected int hour;
	
	/**
	 * current minute int value
	 */
	protected int minute;
	
	/**
	 * Set report date by hour.
	 * 
	 * @param calendarUtil
	 */
	public void setDateByHour(CalendarUtil calendarUtil) {
		this.setYear(calendarUtil.currentYear());
		this.setMonth(calendarUtil.currentMonth());
		this.setWeek(calendarUtil.currentWeek());
		this.setDay(calendarUtil.currentDay());
		this.setHour(calendarUtil.currentHour());
	}
	
	/**
	 * Set report date by 5 minute.
	 * 
	 * @param calendarUtil
	 */
	public void setDateBy5Minute(CalendarUtil calendarUtil) {
		this.setYear(calendarUtil.currentYear());
		this.setMonth(calendarUtil.currentMonth());
		this.setWeek(calendarUtil.currentWeek());
		this.setDay(calendarUtil.currentDay());
		this.setHour(calendarUtil.currentHour());
		this.setMinute((calendarUtil.currentMinute()/5) * 5);
	}
	
	/**
	 * Set report date by minute.
	 * 
	 * @param calendarUtil
	 */
	public void setDateByMinute(CalendarUtil calendarUtil) {
		this.setYear(calendarUtil.currentYear());
		this.setMonth(calendarUtil.currentMonth());
		this.setWeek(calendarUtil.currentWeek());
		this.setDay(calendarUtil.currentDay());
		this.setHour(calendarUtil.currentHour());
		this.setMinute((calendarUtil.currentMinute()));
	}
	
	/**
	 * Update date and invariant fields.
	 * 
	 * @param calendarUtil
	 * @param span
	 */
	abstract void updateReportInit(CalendarUtil calendarUtil, Span span);
	
	/**
	 * Update variable fields.
	 * 
	 * @param span
	 */
	abstract void updateReportNext(Span span);
	
	/**
	 * save report to db.
	 */
	abstract void saveReport();
	
	/**
	 * Getter and setter method for mybatis ORM map.
	 * 
	 * @return
	 */
	public int getMinute() {
		return minute;
	}
	
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getWeek() {
		return week;
	}
	
	public void setWeek(int week) {
		this.week = week;
	}
	
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportDate [year=" + year + ", month=" + month + ", " +
				           "week=" + week + ", day=" + day + ", " +
				           "hour=" + hour + ", minute=" + minute + "]";
	}
	
}
