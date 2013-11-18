package com.vipshop.microscope.mysql.condition;

public class SourceReportCondition {
	
	private int year = -1;
	private int month = -1;
	private int week = -1;
	private int day = -1;
	private int hour = -1;
	
	private String serviceType;
	private String serviceIPAdress;
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceIPAdress() {
		return serviceIPAdress;
	}
	public void setServiceIPAdress(String serviceIPAdress) {
		this.serviceIPAdress = serviceIPAdress;
	}
	
}
