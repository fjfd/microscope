package com.vipshop.microscope.query.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.HbaseRepository;

public class TraceService {
	
	public Map<String, Object> getApps() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("app", HbaseRepository.findAll());
		
//		List<Map<String, Object>> traceLists = new ArrayList<Map<String, Object>>();
//		
//		List<TraceTable> tableTraces = HbaseRepository.findByQuery();
//		
//		for (TraceTable tableTrace : tableTraces) {
//			Map<String, Object> trace = new LinkedHashMap<String, Object>();
//			String traceId = tableTrace.getTraceId();
//			String stmp = tableTrace.getStartTimestamp();
//			String etmp = tableTrace.getEndTimestamp();
//			String dura = tableTrace.getDuration();
//			trace.put("traceId", traceId);
//			trace.put("startTimestamp", stmp);
//			trace.put("endTimestamp", etmp);
//			trace.put("durationMicro", dura);
//			trace.put("serviceCounts", HbaseRepository.findSpanNameByTraceId(traceId));
//			traceLists.add(trace);
//		}
		
		result.put("traceList", null);
		return result;
	}
	
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
			if (span.getDebug() != null) {
				spanInfo.put("debug", span.getDebug().toString());
			}
			spans.add(spanInfo);
		}
		traceSpan.put("spans", spans);
		return traceSpan;
	}
	
}
