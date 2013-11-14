package com.vipshop.microscope.web.action;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.web.result.ListResult;
import com.vipshop.microscope.web.service.ReportService;

@Controller
public class ReportController {
	
	private ReportService service = new ReportService();
	
	@RequestMapping("/report/traceReport")
	@ResponseBody
	public ListResult traceReportUseType(String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getReportUseType();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
}
