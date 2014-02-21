package com.vipshop.microscope.report.condition;

import javax.servlet.http.HttpServletRequest;

import com.vipshop.microscope.common.span.Category;
import com.vipshop.microscope.common.util.IPAddressUtil;

public class TraceReportCondition {
	
	private String appName;
	private String name;
	
	private int appIp = -1;
	private int type = -1;
	private int year = -1;
	private int month = -1;
	private int week = -1;
	private int day = -1;
	private int hour = -1;
	
	private String groupBy;
	
	public TraceReportCondition() {
		
	}
	
	public TraceReportCondition(HttpServletRequest request) {
		String appName = request.getParameter("appName");
		String ipAdress = request.getParameter("ipAdress");
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String week = request.getParameter("week");
		String day = request.getParameter("day");
		String hour = request.getParameter("hour");
		
		if (appName != null) {
			this.setAppName(appName);
		}
		
		if (ipAdress != null) {
			if (!ipAdress.equals("All")) {
				this.setAppIp(IPAddressUtil.intIPAddress(ipAdress));
			}
		}
		
		if (type != null) {
			this.setType(Category.getIntValue(type));
		} 
		
		if (name != null) {
			this.setName(name);
		}
		
		if (year != null) {
			this.setYear(Integer.valueOf(year));
		}
		
		if (month != null) {
			this.setMonth(Integer.valueOf(month));
		}
		
		if (week != null) {
			this.setWeek(Integer.valueOf(week));
		}
		
		if (day != null) {
			this.setDay(Integer.valueOf(day));
		}
		
		if (hour != null) {
			this.setHour(Integer.valueOf(hour));
		}
		
		if (name != null) {
			this.setGroupBy("name");
		} else {
			this.setGroupBy("type");
		}
	}
	
	
	public int getAppIp() {
		return appIp;
	}

	public void setAppIp(int ipAdress) {
		this.appIp = ipAdress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setAppName(String type) {
		this.appName = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
}
