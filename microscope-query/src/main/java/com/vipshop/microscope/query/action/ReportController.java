package com.vipshop.microscope.query.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.query.result.ListResult;
import com.vipshop.microscope.query.result.MapResult;
import com.vipshop.microscope.query.service.ReportService;

/**
 * Responsible for report data.
 * 
 * @author Xu Fei
 * @version 1.0
 */
@Controller
public class ReportController {
	
	@Autowired
	private ReportService service;
	
	@RequestMapping("/report/topReport")
	@ResponseBody
	public MapResult topReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		Map<String, Object> data = service.getTopReport();
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/jvmReportInitLoad")
	@ResponseBody
	public ListResult jvmReportInitLoad(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getJVMMetricsInitLoad(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}

	@RequestMapping("/report/jvmReport")
	@ResponseBody
	public ListResult jvmReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getJVMMetrics(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/jvmReportByTime")
	@ResponseBody
	public ListResult jvmReportByTime(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getJVMMetricsByTime(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}

	
	@RequestMapping("/report/httpReport")
	@ResponseBody
	public ListResult httpReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getJVMMetrics(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/servletReport")
	@ResponseBody
	public ListResult servletReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getServletMetrics(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}

	
	@RequestMapping("/report/cacheReport")
	@ResponseBody
	public ListResult cacheReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getJVMMetrics(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/dbReport")
	@ResponseBody
	public ListResult dbReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getJVMMetrics(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/traceReport")
	@ResponseBody
	public ListResult traceReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getJVMMetrics(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/msgReport")
	@ResponseBody
	public ListResult msgReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> data = service.getJVMMetrics(request);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
}
