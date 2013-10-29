package com.vipshop.microscope.web.query;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class TraceQuery {
	
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
}
