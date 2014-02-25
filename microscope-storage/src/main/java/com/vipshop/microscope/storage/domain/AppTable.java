package com.vipshop.microscope.storage.domain;

import com.vipshop.microscope.common.thrift.Span;

/**
 * AppTrace stands for app name and trace name.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class AppTable {

	private String appName;
	
	private String traceName;

	public AppTable(String appName, String traceName) {
		this.appName = appName;
		this.traceName = traceName;
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
	
	public static AppTable build(Span span) {
		String traceId = String.valueOf(span.getTraceId());
		String spanId = String.valueOf(span.getSpanId());
		
		if (traceId.equals(spanId)) {
			String appName = span.getAppName();
			String traceName = span.getSpanName();
			
			// remove user id from span name
			if (appName.equals("user_info")) {
				traceName = traceName.replaceAll("\\d", "");
				traceName = traceName.substring(7);
			}
			return new AppTable(appName, traceName);
		}
		
		return null;
	}


	@Override
	public String toString() {
		return "App [appName=" + appName + ", traceName=" + traceName + "]";
	}
	
	public static void main(String[] args) {
		String traceName = "/users/11111111111/info/@system";
		traceName = traceName.replaceAll("\\d", "");
		traceName = traceName.substring(7);
		System.out.println(traceName);
	}
	
}
