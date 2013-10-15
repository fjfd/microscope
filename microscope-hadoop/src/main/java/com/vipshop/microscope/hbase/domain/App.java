package com.vipshop.microscope.hbase.domain;

public class App {

	public static final String TABLE_NAME = "app";
	public static final String CF_INFO = "cfInfo";
	public static final String CF_INFO_APP_NAME = "app_name";

	private String appName;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public App(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "AppIndex [appName=" + appName + "]";
	}



}
