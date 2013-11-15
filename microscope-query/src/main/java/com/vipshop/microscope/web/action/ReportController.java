package com.vipshop.microscope.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.web.condition.QueryConditionBuilder;
import com.vipshop.microscope.web.result.ListResult;
import com.vipshop.microscope.web.service.ReportService;

@Controller
public class ReportController {
	
	private ReportService service = new ReportService();
	
	@RequestMapping("/report/traceReport")
	@ResponseBody
	public ListResult traceReport(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		
		TraceReportCondition queryCondition = QueryConditionBuilder.buildTraceReport(request);
		List<Map<String, Object>> condition = service.getTraceReport(queryCondition);
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
}
