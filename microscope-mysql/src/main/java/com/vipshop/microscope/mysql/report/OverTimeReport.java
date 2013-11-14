package com.vipshop.microscope.mysql.report;

import com.vipshop.microscope.common.util.CalendarUtil;

public class OverTimeReport {

	private int year;
	private int month;
	private int week;
	private int day;
	private int hour;
	private int minute;
	
	private String app;
	private String ipAdress;
	private String type;
	private String name;

	private float avgDura;
	private int hitCount;
	private int failCount;
	
	public int getRegion(int minute) {
		return 0;
	}
	
	public static String makeId(CalendarUtil calendarUtil, String traceName) {
		return calendarUtil.uniqueTimeStampToMin() + "-" + traceName;
	}

	public static String makePreId(CalendarUtil calendarUtil, String traceName) {
		return calendarUtil.uniquePreTimeStampToMin() + "-" + traceName;
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

	public float getAvgDura() {
		return avgDura;
	}

	public void setAvgDura(float durationCount) {
		avgDura = durationCount;
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

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

}
