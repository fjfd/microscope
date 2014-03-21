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
import com.vipshop.microscope.query.service.TraceSerivice;

/**
 * Query Data API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
@Controller
public class TraceController {

	@Autowired
	TraceSerivice service;
	
	// ********************************** home      relative query **************************************** //

	// ********************************** report    relative query **************************************** //
	
	// ********************************** exception relative query **************************************** //
	
	@RequestMapping("/trace/excepCondition")
	@ResponseBody
	public ListResult queryExcepCondition(String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getExcepCondition();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}

	
	@RequestMapping("/trace/excepList")
	@ResponseBody
	public ListResult queryExceptionList(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getExceptionList(request);
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
	// ********************************** alerts    relative query **************************************** //
	
	// ********************************** trace     relative query **************************************** //
	
	@RequestMapping("/trace/apps")
	@ResponseBody
	public ListResult traceQueryCondition(String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getQueryCondition();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/trace/traceList")
	@ResponseBody
	public ListResult traceList(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> traceLists = service.getTraceList(request);
		result.setResult(traceLists);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/trace/traceSpan")
	@ResponseBody
	public MapResult traceSpan(HttpServletRequest request, String callback) {
		String traceId = request.getParameter("traceId");
		MapResult result = new MapResult();
		Map<String, Object> traceSpan = service.getTraceSpan(traceId);
		result.setResult(traceSpan);
		result.setCallback(callback);
		return result;
	}

}