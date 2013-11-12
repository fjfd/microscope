package com.vipshop.microscope.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.web.result.TraceReportResult;
import com.vipshop.microscope.web.service.ReportService;

@Controller
public class ReportController {
	
	private ReportService service = new ReportService();
	
	@RequestMapping("/report/traceReportUseType")
	@ResponseBody
	public TraceReportResult traceReportUseType(String callback) {
		TraceReportResult result = new TraceReportResult();
		List<Map<String, Object>> condition = service.getReportUseType();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/traceReportUseTypeAndTime")
	@ResponseBody
	public TraceReportResult traceReportUseTypeAndTime(String callback) {
		TraceReportResult result = new TraceReportResult();
		List<Map<String, Object>> condition = service.getReportUseType();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}

	@RequestMapping("/report/traceReportUseName")
	@ResponseBody
	public TraceReportResult traceReportUseName(HttpServletRequest request, String callback) {
		TraceReportResult result = new TraceReportResult();
		String name = request.getParameter("name");
		
		List<Map<String, Object>> condition = null;
		if (name != null) {
			condition = service.getReportUseName(name);
		} else {
			condition = service.getReportUseName();
		}
		
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/traceReportUseNameAndTime")
	@ResponseBody
	public TraceReportResult traceReportUseNameAndTime(HttpServletRequest request, String callback) {
		TraceReportResult result = new TraceReportResult();
		String name = request.getParameter("name");
		
		List<Map<String, Object>> condition = null;
		if (name != null) {
			condition = service.getReportUseName(name);
		} else {
			condition = service.getReportUseName();
		}
		
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}

	
}
