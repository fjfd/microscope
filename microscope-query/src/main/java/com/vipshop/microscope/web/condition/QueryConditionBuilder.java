package com.vipshop.microscope.web.condition;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vipshop.microscope.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.mysql.condition.TraceReportCondition;

public class QueryConditionBuilder {
	
	public static Map<String, String> build(HttpServletRequest request) {
		String appName = request.getParameter("appName");
		String traceName = request.getParameter("traceName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String limit = request.getParameter("limit");
		
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", appName);
		query.put("traceName", traceName);
		query.put("startTime", startTime);
		query.put("endTime", endTime);
		query.put("limit", limit);
		
		return query;
	}
	
	public static TraceReportCondition buildTraceReport(HttpServletRequest request) {
		TraceReportCondition condition = new TraceReportCondition();
		
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
			condition.setAppName(appName);
		}
		
		if (ipAdress != null && !ipAdress.equals("All")) {
			condition.setIpAdress(ipAdress);
		}
		
		if (type != null) {
			condition.setType(type);
			condition.setGroupBy("name");
		} else {
			condition.setGroupBy("type");
		}
		
		if (name != null) {
			condition.setName(name);
		}
		
		if (year != null) {
			condition.setYear(Integer.valueOf(year));
		}
		
		if (month != null) {
			condition.setMonth(Integer.valueOf(month));
		}
		
		if (week != null) {
			condition.setWeek(Integer.valueOf(week));
		}
		
		if (day != null) {
			condition.setDay(Integer.valueOf(day));
		}
		
		if (hour != null) {
			condition.setHour(Integer.valueOf(hour));
		}
		
		return condition;
	}
	
	public static MsgReportCondition buildMsgReport(HttpServletRequest request) {
		MsgReportCondition condition = new MsgReportCondition();
		
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String week = request.getParameter("week");
		String day = request.getParameter("day");
		
		if (year != null) {
			condition.setYear(Integer.valueOf(year));
		}
		
		if (month != null) {
			condition.setMonth(Integer.valueOf(month));
		}
		
		if (week != null) {
			condition.setWeek(Integer.valueOf(week));
		}
		
		if (day != null) {
			condition.setDay(Integer.valueOf(day));
		}
		
		return condition;
	}
	
}
