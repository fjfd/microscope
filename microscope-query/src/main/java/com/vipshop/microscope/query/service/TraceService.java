package com.vipshop.microscope.query.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vipshop.microscope.storage.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.HbaseRepository;
import com.vipshop.microscope.thrift.gen.Span;

public class TraceService {
	
	public List<Map<String, Object>> getQueryCondition() {
		return HbaseRepository.findAll();
	}
	
	public List<Map<String, Object>> getTraceList(HttpServletRequest request) {
		List<Map<String, Object>> traceLists = new ArrayList<Map<String, Object>>();
		
		String appName = request.getParameter("appName");
		String traceName = request.getParameter("traceName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String limit = request.getParameter("limit");
		
		Map<String, String> query = new HashMap<String, String>();
		query.put("appName", appName);
		query.put("traceName", traceName);
		query.put("startTime", startTime);
		query.put("endTime", endTime);
		query.put("limit", limit);

		List<TraceTable> tableTraces = HbaseRepository.findByQuery(query);
		Collections.sort(tableTraces);
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
			trace.put("serviceCounts", HbaseRepository.findSpanNameByTraceId(traceId));
			traceLists.add(trace);
		}
		return traceLists;
	}
	
	public Map<String, Object> getTraceSpan(String traceId) {
		Map<String, Object> traceSpan = new LinkedHashMap<String, Object>();
		traceSpan.put("traceId", traceId);
		List<Map<String, Object>> spans = new ArrayList<Map<String,Object>>();
		List<Span> spanTables = HbaseRepository.findSpanByTraceId(traceId);
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
			spans.add(spanInfo);
		}
		traceSpan.put("spans", spans);
		return traceSpan;
	}

}
