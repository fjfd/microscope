package com.vipshop.microscope.hbase.domain;

public class TraceIndex {

	public static final String TABLE_NAME = "trace.index";
	public static final String CF_INFO = "cfInfo";
	public static final String CF_INFO_TRACE_NAME = "trace_name";
	public static final String CF_INFO_APP_NAME = "app_name";

	private String appName;
	private String traceName;

	public TraceIndex(String appName, String traceName) {
		super();
		this.appName = appName;
		this.traceName = traceName;
	}

	public String getTraceName() {
		return traceName;
	}

	public void setTraceName(String traceName) {
		this.traceName = traceName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String traceId) {
		this.appName = traceId;
	}

	@Override
	public String toString() {
		return "TraceIndex [appName=" + appName + ", traceName=" + traceName + "]";
	}

}
