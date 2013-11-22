package com.vipshop.microscope.mysql.report;

public class SourceReport extends AbstraceReport {
	
	private String app;
	private String name;
	
	private String serverType;
	private String serverIp;
	private String sqlType;
	
	private long count;
	
	private long fail;
	
	private float failpre;
	
	private float avgDura;
	private long sumDura;
	
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

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serviceIPAdress) {
		this.serverIp = serviceIPAdress;
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

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String serviceName) {
		this.sqlType = serviceName;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serviceType) {
		this.serverType = serviceType;
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

	public long getSumDura() {
		return sumDura;
	}

	public void setSumDura(long sumDura) {
		this.sumDura = sumDura;
	}

	@Override
	public String toString() {
		return "SourceReport [app=" + app + ", name=" + name + ", serverType=" + serverType + ", serverIp=" + serverIp + ", sqlType=" + sqlType + ", count=" + count + ", fail=" + fail + ", failpre="
				+ failpre + ", avgDura=" + avgDura + ", sumDura=" + sumDura + ", startTime=" + startTime + ", endTime=" + endTime + ", tps=" + tps + "]";
	}
	
}
