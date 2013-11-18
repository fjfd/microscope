package com.vipshop.microscope.mysql.report;

public class SourceReport extends AbstraceReport {
	
	private String app;
	private String name;
	
	private String serviceType;
	private String serviceIPAdress;
	private String serviceName;
	
	private long count;
	
	private long fail;
	
	private float failpre;
	
	private float avgDura;
	
	private long startTime;
	private long endTime;
	
	private float tps;
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getServiceIPAdress() {
		return serviceIPAdress;
	}

	public void setServiceIPAdress(String serviceIPAdress) {
		this.serviceIPAdress = serviceIPAdress;
	}

	public long getFail() {
		return fail;
	}

	public void setFail(long fail) {
		this.fail = fail;
	}

	public float getFailpre() {
		return failpre;
	}

	public void setFailpre(float failpre) {
		this.failpre = failpre;
	}

	public float getAvgDura() {
		return avgDura;
	}

	public void setAvgDura(float avgDuration) {
		this.avgDura = avgDuration;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public float getTps() {
		return tps;
	}

	public void setTps(float tps) {
		this.tps = tps;
	}

	@Override
	public String toString() {
		return "SourceReport [app=" + app + ", name=" + name + ", serviceType=" + serviceType + ", serviceIPAdress=" + serviceIPAdress + ", serviceName=" + serviceName + ", count=" + count
				+ ", fail=" + fail + ", failpre=" + failpre + ", avgDura=" + avgDura + ", startTime=" + startTime + ", endTime=" + endTime + ", tps=" + tps + ", getMinute()=" + getMinute()
				+ ", getYear()=" + getYear() + ", getMonth()=" + getMonth() + ", getWeek()=" + getWeek() + ", getDay()=" + getDay() + ", getHour()=" + getHour() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
