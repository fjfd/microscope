package com.vipshop.microscope.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.mysql.condition.SourceReportCondition;
import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.web.condition.ConditionBuilder;
import com.vipshop.microscope.web.result.ListResult;
import com.vipshop.microscope.web.result.MapResult;
import com.vipshop.microscope.web.service.ReportService;

@Controller
public class ReportController {
	
	private ReportService service = new ReportService();
	
	@RequestMapping("/report/marketReport")
	@ResponseBody
	public MapResult marketReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		MsgReportCondition query = ConditionBuilder.buildMsgReport(request);
		Map<String, Object> data = service.getMsgReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}	
	
	@RequestMapping("/report/topReport")
	@ResponseBody
	public MapResult topReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		MsgReportCondition query = ConditionBuilder.buildMsgReport(request);
		Map<String, Object> data = service.getMsgReport(query);
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
		TraceReportCondition query = ConditionBuilder.buildTraceReport(request);
		List<Map<String, Object>> data = service.getTraceReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/overTimeReport")
	@ResponseBody
	public MapResult overTimeReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		TraceReportCondition query = ConditionBuilder.buildTraceReport(request);
		Map<String, Object> data = service.getOverTimeReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/sourceReport")
	@ResponseBody
	public MapResult sourceReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		SourceReportCondition query = ConditionBuilder.buildSourceReport(request);
		Map<String, Object> data = service.getSourceReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/depenReport")
	@ResponseBody
	public MapResult depenReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		MsgReportCondition query = ConditionBuilder.buildMsgReport(request);
		Map<String, Object> data = service.getMsgReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/report/msgReport")
	@ResponseBody
	public MapResult msgReport(HttpServletRequest request, String callback) {
		MapResult result = new MapResult();
		MsgReportCondition query = ConditionBuilder.buildMsgReport(request);
		Map<String, Object> data = service.getMsgReport(query);
		result.setResult(data);
		result.setCallback(callback);
		return result;
	}
	
}
