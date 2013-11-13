package com.vipshop.microscope.mysql.domain;


/**
 * Invoke source distribution.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class DistributionReport {
	
	private String id;
	
	private int year;
	private int month;
	private int week;
	private int day;
	private int hour;
	
	private String appName;
	private String serviceName;
	private String serviceType;
	
	private String sqlType;
	
	private long totalCount;
	private long failureCount;
	
	private float failurePrecent;
	
	private float avg;
	private float tps;
	
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
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSqlType() {
		return sqlType;
	}
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
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
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	public float getTps() {
		return tps;
	}
	public void setTps(float tps) {
		this.tps = tps;
	}
	
}
