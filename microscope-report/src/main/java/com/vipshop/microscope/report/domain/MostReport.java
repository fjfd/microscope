package com.vipshop.microscope.report.domain;

/**
 * Most Report
 * 
 * URL invoke times 
 * Service invoke tims
 * Cache invoke times
 * DB invoke times
 * Call invoke times
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MostReport {
	
	private int type;
	
	private String app;
	private String name;
	
	private long count;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "MostReport [type=" + type + ", app=" + app + ", name=" + name + ", count=" + count + "]";
	}
	
}
