package com.vipshop.microscope.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.web.result.ListResult;
import com.vipshop.microscope.web.service.ReportService;

@Controller
public class ReportController {
	
	private ReportService service = new ReportService();
	
	@RequestMapping("/report/traceReportUseType")
	@ResponseBody
	public ListResult traceReportUseType(String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getReportUseType();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/traceReportUseTypeAndTime")
	@ResponseBody
	public ListResult traceReportUseTypeAndTime(String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getReportUseType();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}

	@RequestMapping("/report/traceReportUseName")
	@ResponseBody
	public ListResult traceReportUseName(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
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
	public ListResult traceReportUseNameAndTime(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
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
