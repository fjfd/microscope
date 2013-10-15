package com.vipshop.microscope.web.query;

public class TraceQuery {
	
	private String appName;
	private String traceName;
	
	private String startTime;
	private String endTime;
	
	private String limit;
	
	public TraceQuery(String traceName, String startTime, String endTime, String limit) {
		super();
		this.traceName = traceName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.limit = limit;
	}

	public TraceQuery(String appName, String traceName, String startTime, String endTime, String limit) {
		super();
		this.appName = appName;
		this.traceName = traceName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.limit = limit;
	}
	
	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTraceName() {
		return traceName;
	}

	public void setTraceName(String traceName) {
		this.traceName = traceName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "TraceQuery [appName=" + appName + ", traceName=" + traceName + ", startTime=" + startTime + ", endTime=" + endTime + ", limit=" + limit + "]";
	}
	
}
