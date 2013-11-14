package com.vipshop.microscope.mysql.report;

public class MsgReport {
	
	private int year;
	private int month;
	private int week;
	private int day;
	private int hour;
	
	private long msgNum;
	private long msgSize;
	
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


	public long getMsgNum() {
		return msgNum;
	}


	public void setMsgNum(long msgNum) {
		this.msgNum = msgNum;
	}


	public long getMsgSize() {
		return msgSize;
	}


	public void setMsgSize(long msgSize) {
		this.msgSize = msgSize;
	}

	@Override
	public String toString() {
		return "MsgReport [year=" + year + ", month=" + month + ", week=" + week + ", day=" + day + ", hour=" + hour + ", msgNum=" + msgNum + ", msgSize=" + msgSize + "]";
	}

}
