package com.vipshop.microscope.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.vipshop.microscope.storage.QueryRepository;

@Service
public class ExceptionService {
	
	private final QueryRepository queryRepository = QueryRepository.getQueryRepository();
	
	public List<Map<String, Object>> getQueryCondition() {
		return queryRepository.findExceptionIndex();
	}

	public List<Map<String, Object>> getExceptionList(HttpServletRequest request) {
		String appName = request.getParameter("appName");
		String ipAdress = request.getParameter("ipAddress");
		String name = request.getParameter("name");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String limit = request.getParameter("limit");

		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", appName);
		query.put("ipAddress", ipAdress);
		query.put("name", name);
		query.put("startTime", startTime);
		query.put("endTime", endTime);
		query.put("limit", limit);
		
		return queryRepository.findExceptionList(query);
	}
}
