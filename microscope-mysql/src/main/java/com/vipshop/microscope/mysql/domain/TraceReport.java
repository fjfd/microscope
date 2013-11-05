package com.vipshop.microscope.mysql.domain;

public class TraceReport {

	private String type;
	private long totalCount;
	private long failureCount;
	private float failurePrecent;
	private float min;
	private float max;
	private float avg;
	private int year;
	private int month;
	private int day;
	private int hour;

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

	public String getType() {
		return type;
	}

	public void setType(String name) {
		this.type = name;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(long failureCount) {
		this.failureCount = failureCount;
	}

	public float getFailurePrecent() {
		return failurePrecent;
	}

	public void setFailurePrecent(float failurePrecent) {
		this.failurePrecent = failurePrecent;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}
	
	@Override
	public String toString() {
		return "TraceStat [name=" + type + ", totalCount=" + totalCount + ", failureCount=" + failureCount + ", failurePrecent=" + failurePrecent + ", min=" + min + ", max=" + max + ", avg=" + avg
				+ "]";
	}


}
