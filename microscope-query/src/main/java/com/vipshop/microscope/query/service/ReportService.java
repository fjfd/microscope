package com.vipshop.microscope.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.opentsdb.core.Aggregator;
import com.vipshop.microscope.storage.opentsdb.core.Aggregators;

@Service
public class ReportService {
	
	private final StorageRepository storageRepository = StorageRepository.getStorageRepository();
	
	public Map<String, Object> suggestMetrics(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String search = request.getParameter("search");
		
		result.put("suggestmetrics", storageRepository.suggestMetrics(search));
		return result;
	}
	
	public Map<String, Object> metrics(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		String metric = request.getParameter("metric");
		Map<String, String> tags = new HashMap<String, String>();
		tags.put("APP", request.getParameter("app"));
		tags.put("IP", request.getParameter("ip"));
		Aggregator function = Aggregators.SUM;
		boolean rate = true;
		
		result.put("metrics", storageRepository.find(System.currentTimeMillis() - 60 * 1000 * 10, metric, tags, function, rate));
		return result;
	}
	public List<Map<String, Object>> getQueryCondition() {
		return storageRepository.findReportIndex();
	}

	public Map<String, Object> getTopReport() {
		return storageRepository.findTopList();
	}
	
	public List<Map<String, Object>> getJVMMetricsInitLoad(HttpServletRequest request) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", request.getParameter("appName"));
		query.put("ipAddress", request.getParameter("ipAddress"));
		return storageRepository.findJVMListInitLoad(query);
	}

	public List<Map<String, Object>> getJVMMetrics(HttpServletRequest request) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", request.getParameter("appName"));
		query.put("ipAddress", request.getParameter("ipAddress"));
		return storageRepository.findJVMList(query);
	}
	
	public List<Map<String, Object>> getJVMMetricsByTime(HttpServletRequest request) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", request.getParameter("appName"));
		query.put("ipAddress", request.getParameter("ipAddress"));
		query.put("startTime", request.getParameter("startTime"));
		query.put("endTime", request.getParameter("endTime"));
		return storageRepository.findJVMListByTime(query);
	}
	
	public List<Map<String, Object>> getServletMetrics(HttpServletRequest request) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", request.getParameter("appName"));
		query.put("ipAddress", request.getParameter("ipAddress"));
		return storageRepository.findServletList(query);
	}
	
}
