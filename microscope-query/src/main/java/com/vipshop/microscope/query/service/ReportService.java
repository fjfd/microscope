package com.vipshop.microscope.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.vipshop.microscope.storage.QueryRepository;

@Service
public class ReportService {
	
	private final QueryRepository queryRepository = QueryRepository.getQueryRepository();
	
	public Map<String, Object> getTopReport() {
		return queryRepository.findTopList();
	}
	
	public List<Map<String, Object>> getJVMMetricsInitLoad(HttpServletRequest request) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", request.getParameter("appName"));
		query.put("ipAddress", request.getParameter("ipAddress"));
		return queryRepository.findJVMListInitLoad(query);
	}

	public List<Map<String, Object>> getJVMMetrics(HttpServletRequest request) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", request.getParameter("appName"));
		query.put("ipAddress", request.getParameter("ipAddress"));
		return queryRepository.findJVMList(query);
	}
	
	public List<Map<String, Object>> getJVMMetricsByTime(HttpServletRequest request) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", request.getParameter("appName"));
		query.put("ipAddress", request.getParameter("ipAddress"));
		query.put("startTime", request.getParameter("startTime"));
		query.put("endTime", request.getParameter("endTime"));
		return queryRepository.findJVMListByTime(query);
	}
	
	public List<Map<String, Object>> getServletMetrics(HttpServletRequest request) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", request.getParameter("appName"));
		query.put("ipAddress", request.getParameter("ipAddress"));
		return queryRepository.findServletList(query);
	}
	
}
