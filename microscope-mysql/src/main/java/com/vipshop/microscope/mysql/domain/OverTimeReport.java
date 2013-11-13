package com.vipshop.microscope.mysql.domain;

import com.vipshop.microscope.common.util.CalendarUtil;

public class OverTimeReport {

	private String id;

	private int year;
	private int month;
	private int week;
	private int day;
	private int hour;
	private int minute;
	
	private String type;
	private String name;

	private float AvgDuration;
	private int HitCount;
	private int FailCount;
	
	public int getRegion(int minute) {
		return 0;
	}
	
	public static String makeId(CalendarUtil calendarUtil, String traceName) {
		return calendarUtil.uniqueTimeStampToMin() + "-" + traceName;
	}

	public static String makePreId(CalendarUtil calendarUtil, String traceName) {
		return calendarUtil.uniquePreTimeStampToMin() + "-" + traceName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public float getAvgDuration() {
		return AvgDuration;
	}

	public void setAvgDuration(float durationCount) {
		AvgDuration = durationCount;
	}

	public int getHitCount() {
		return HitCount;
	}

	public void setHitCount(int hitCount) {
		HitCount = hitCount;
	}

	public int getFailCount() {
		return FailCount;
	}

	public void setFailCount(int failCount) {
		FailCount = failCount;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
