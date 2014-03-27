package com.vipshop.microscope.query.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.QueryRepository;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;

@Service
public class TraceSerivice {
	
	private final QueryRepository queryRepository = QueryRepository.getQueryRepository();
	
	public List<Map<String, Object>> getQueryCondition() {
		return queryRepository.findAppIPTrace();
	}
	
	public List<Map<String, Object>> getTraceList(HttpServletRequest request) {
		List<Map<String, Object>> traceLists = new ArrayList<Map<String, Object>>();
		
		String appName = request.getParameter("appName");
		String ipAdress = request.getParameter("ipAddress");
		String traceName = request.getParameter("traceName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String limit = request.getParameter("limit");
		
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", appName);
		query.put("ipAddress", ipAdress);
		query.put("traceName", traceName);
		query.put("startTime", startTime);
		query.put("endTime", endTime);
		query.put("limit", limit);
		
		List<TraceTable> tableTraces = queryRepository.find(query);
		for (TraceTable tableTrace : tableTraces) {
			Map<String, Object> trace = new LinkedHashMap<String, Object>();
			String traceId = tableTrace.getTraceId();
			String stmp = tableTrace.getStartTimestamp();
			String etmp = tableTrace.getEndTimestamp();
			String dura = tableTrace.getDuration();
			trace.put("traceId", traceId);
			trace.put("startTimestamp", stmp);
			trace.put("endTimestamp", etmp);
			trace.put("durationMicro", dura);
			trace.put("serviceCounts", queryRepository.findSpanName(traceId));
			traceLists.add(trace);
		}
		return traceLists;
	}
	
	public Map<String, Object> getTraceSpan(String traceId) {
		Map<String, Object> traceSpan = new LinkedHashMap<String, Object>();
		traceSpan.put("traceId", traceId);
		List<Map<String, Object>> spans = new ArrayList<Map<String,Object>>();
		List<Span> spanTables = queryRepository.find(traceId);
		for (Span span : spanTables) {
			Map<String, Object> spanInfo = new LinkedHashMap<String, Object>();
			spanInfo.put("app", span.getAppName());
			spanInfo.put("type", span.getSpanType());
			spanInfo.put("name", span.getSpanName());
			spanInfo.put("id", span.getSpanId());
			spanInfo.put("parentId", span.getParentId());
			spanInfo.put("status", span.getResultCode());
			spanInfo.put("start_time", span.getStartTime());
			spanInfo.put("end_time", span.getStartTime() + span.getDuration());
			spanInfo.put("ipadress", span.getAppIp());
			spanInfo.put("duration", span.getDuration());
			if (span.getDebug() != null) {
				spanInfo.put("debug", span.getDebug().toString());
			}
			spans.add(spanInfo);
		}
		traceSpan.put("spans", spans);
		return traceSpan;
	}
	
}
