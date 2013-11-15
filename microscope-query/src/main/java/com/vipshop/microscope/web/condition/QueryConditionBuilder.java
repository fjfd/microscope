package com.vipshop.microscope.web.condition;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		
		if (appName != null) {
			condition.setAppName(appName);
			condition.setGroupBy("type");
		}
		
		if (type != null && appName != null) {
			condition.setType(type);
			condition.setGroupBy("name");
		}
		
		if (name != null) {
			condition.setName("%" + name + "%");
			condition.setGroupBy("none");
		}
		
		condition.setYear(2013);
		condition.setMonth(11);
		condition.setDay(15);
		condition.setHour(11);
		
		return condition;
	}
	
	public static TraceReportCondition buildOverTimeReport(HttpServletRequest request) {
		TraceReportCondition condition = new TraceReportCondition();
		
		String appName = request.getParameter("appName");
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		
		condition.setAppName(appName);
		condition.setType(type);
		condition.setGroupBy("none");
		
		if (name != null) {
			condition.setName(name);
		}
		
		condition.setYear(2013);
		condition.setMonth(11);
		condition.setDay(15);
		condition.setHour(11);
		
		return condition;
	}

}
