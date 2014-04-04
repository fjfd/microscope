package com.vipshop.microscope.query.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vipshop.microscope.query.result.ListResult;
import com.vipshop.microscope.query.service.ExceptionService;

@Controller
public class ExceptionController {
	
	@Autowired
	private ExceptionService service;
	
	@RequestMapping("/exception/queryCondition")
	@ResponseBody
	public ListResult queryExcepCondition(String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getQueryCondition();
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
	@RequestMapping("/exception/exceptionList")
	@ResponseBody
	public ListResult queryExceptionList(HttpServletRequest request, String callback) {
		ListResult result = new ListResult();
		List<Map<String, Object>> condition = service.getExceptionList(request);
		result.setResult(condition);
		result.setCallback(callback);
		return result;
	}
	
}
