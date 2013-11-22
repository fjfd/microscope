package com.vipshop.microscope.web.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.factory.HbaseRepository;
import com.vipshop.microscope.thrift.Annotation;
import com.vipshop.microscope.thrift.AnnotationType;
import com.vipshop.microscope.thrift.Span;

public class TraceService {
	
	public List<Map<String, Object>> getQueryCondition() {
		List<Map<String, Object>> result = HbaseRepository.findAll();
		return result;
	}
	
	public List<Map<String, Object>> getTraceList(Map<String, String> query) {
		List<Map<String, Object>> traceLists = new ArrayList<Map<String, Object>>();
		
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
			spanInfo.put("traceId", traceId);
			spanInfo.put("name", span.getName());
			spanInfo.put("id", span.getId());
			spanInfo.put("app", span.getApp_name());
			spanInfo.put("services", span.getName());
			spanInfo.put("type", span.getType());
			spanInfo.put("status", span.getResultCode());
			spanInfo.put("start_time", span.getStartstamp());
			spanInfo.put("end_time", span.getStartstamp() + span.getDuration());
			spanInfo.put("ipadress", span.getIPAddress());
			
			if (!(span.getParent_id() == 0L)) {
				spanInfo.put("parentId", span.getParent_id());
			}
			List<Annotation> annotationTables = span.annotations;
			
			List<Map<String, Object>> annoInfo = new ArrayList<Map<String,Object>>();
			
			long startstmp = 0;
			long endstmp = 0;
			for (Annotation annotation : annotationTables) {
				Map<String, Object> annotationMap = new LinkedHashMap<String, Object>();
				annotationMap.put("timestamp", annotation.getTimestamp());
				annotationMap.put("value", annotation.getType());
				if (annotation.getType().equals(AnnotationType.CS)) {
					startstmp = annotation.getTimestamp();
				}
				
				if (annotation.getType().equals(AnnotationType.CR)) {
					endstmp = annotation.getTimestamp();
				}
				annoInfo.add(annotationMap);
			}
			
			spanInfo.put("startTimestamp", startstmp);
			spanInfo.put("duration", endstmp - startstmp);
			spanInfo.put("annotations", annoInfo);
			spans.add(spanInfo);
		}
		
		traceSpan.put("spans", spans);
		
		TraceTable table = HbaseRepository.findByTraceId(traceId);
		traceSpan.put("startTimestamp", table.getStartTimestamp());
		traceSpan.put("endTimestamp", table.getEndTimestamp());
		traceSpan.put("durationMicro", table.getDuration());
		traceSpan.put("serviceCounts", HbaseRepository.findSpanNameByTraceId(traceId));

		return traceSpan;
		
	}

}
