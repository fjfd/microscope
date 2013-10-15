package com.vipshop.microscope.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.web.builder.TraceQueryBuilder;
import com.vipshop.microscope.web.result.AppAndTraceResult;
import com.vipshop.microscope.web.result.TraceListResult;
import com.vipshop.microscope.web.result.TraceSpanResult;
import com.vipshop.microscope.web.service.TraceService;

@Controller
public class TraceController {

	TraceService service = new TraceService();

	@RequestMapping("/trace/queryCondition")
	@ResponseBody
	public AppAndTraceResult traceQueryCondition(String callback) {
		AppAndTraceResult result = new AppAndTraceResult();
		List<Map<String, Object>> condition = service.getQueryCondition();
		result.setAppAndTrace(condition);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/trace/traceList")
	@ResponseBody
	public TraceListResult traceList(HttpServletRequest request, String callback) {
		TraceListResult result = new TraceListResult();
		Map<String, String> query = TraceQueryBuilder.build(request);
		List<Map<String, Object>> traceLists = service.getTraceList(query);
		result.setTraceList(traceLists);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/trace/traceSpan")
	@ResponseBody
	public TraceSpanResult traceSpan(HttpServletRequest request, String callback) {
		String traceId = request.getParameter("traceId");
		TraceSpanResult result = new TraceSpanResult();
		Map<String, Object> traceSpan = service.getTraceSpan(traceId);
		result.setTraceSpan(traceSpan);
		result.setCallback(callback);
		return result;
	}

}