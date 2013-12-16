package com.vipshop.microscope.query.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.query.result.ListResult;
import com.vipshop.microscope.query.result.MapResult;
import com.vipshop.microscope.query.service.ReportService;
import com.vipshop.microscope.report.condition.DepenReportCondition;
import com.vipshop.microscope.report.condition.MarketReportCondition;
import com.vipshop.microscope.report.condition.MsgReportCondition;
import com.vipshop.microscope.report.condition.ProblemReportCondition;
import com.vipshop.microscope.report.condition.SourceReportCondition;
import com.vipshop.microscope.report.condition.TopReportCondition;
import com.vipshop.microscope.report.condition.TraceReportCondition;

/**
 * Responsible for report data.
 * 
 * @author Xu Fei
 * @version 1.0
 */
@Controller
public class ReportController {
	
	private ReportService service = new ReportService();
	
	@RequestMapping("/report/marketReport")
	@ResponseBody
	public MapResult marketReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		MarketReportCondition query = new MarketReportCondition(request);
		Map<String, Object> data = service.getMarketReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}	
	
	@RequestMapping("/report/topReport")
	@ResponseBody
	public MapResult topReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		TopReportCondition query = new TopReportCondition(request);
		Map<String, Object> data = service.getTopReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}

	@RequestMapping("/report/appAndIP")
	@ResponseBody
	public MapResult appAndIP(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		Map<String, Object> data = service.getAppAndIP();
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}

	@RequestMapping("/report/traceReport")
	@ResponseBody
	public ListResult traceReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		TraceReportCondition query = new TraceReportCondition(request);
		List<Map<String, Object>> data = service.getTraceReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/overTimeReport")
	@ResponseBody
	public MapResult overTimeReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		TraceReportCondition query = new TraceReportCondition(request);
		Map<String, Object> data = service.getOverTimeReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/problemReport")
	@ResponseBody
	public MapResult problemReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		ProblemReportCondition query = new ProblemReportCondition(request);
		Map<String, Object> data = service.getProblemReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/problemOverTimeReport")
	@ResponseBody
	public MapResult problemOverTimeReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		ProblemReportCondition query = new ProblemReportCondition(request);
		Map<String, Object> data = service.getProblemOverTimeReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}

	@RequestMapping("/report/sourceReport")
	@ResponseBody
	public MapResult sourceReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		SourceReportCondition query = new SourceReportCondition(request);
		Map<String, Object> data = service.getSourceReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/depenReport")
	@ResponseBody
	public MapResult depenReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		DepenReportCondition query = new DepenReportCondition(request);
		Map<String, Object> data = service.getDepenReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/msgReport")
	@ResponseBody
	public MapResult msgReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		MsgReportCondition query = new MsgReportCondition(request);
		Map<String, Object> data = service.getMsgReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
}
