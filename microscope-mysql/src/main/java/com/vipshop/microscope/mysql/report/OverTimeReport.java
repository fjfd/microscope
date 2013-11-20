package com.vipshop.microscope.mysql.report;

import com.vipshop.microscope.common.util.CalendarUtil;

public class OverTimeReport extends AbstraceReport {

	private String app;
	private String ipAdress;
	private String type;
	private String name;

	private float avgDura;
	private long sumDura;
	private int hitCount;
	private int failCount;
	
	public int getRegion(int minute) {
		return 0;
	}
	
	public static String makeId(CalendarUtil calendarUtil, String traceName) {
		return calendarUtil.uniqueTimeStampToMin() + "-" + traceName;
	}

	public static String makePreId(CalendarUtil calendarUtil, String traceName) {
		return calendarUtil.uniquePreTimeStampToMin() + "-" + traceName;
	}

	public float getAvgDura() {
		return avgDura;
	}

	public void setAvgDura(float durationCount) {
		avgDura = durationCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	@Override
	public String toString() {
		return "OverTimeReport [year=" + year + ", month=" + month + ", week=" + week + ", day=" + day + ", hour=" + hour + ", minute=" + minute + ", app=" + app + ", ipAdress=" + ipAdress
				+ ", type=" + type + ", name=" + name + ", avgDura=" + avgDura + ", hitCount=" + hitCount + ", failCount=" + failCount + "]";
	}

	public long getSumDura() {
		return sumDura;
	}

	public void setSumDura(long sumDura) {
		this.sumDura = sumDura;
	}
	
}
