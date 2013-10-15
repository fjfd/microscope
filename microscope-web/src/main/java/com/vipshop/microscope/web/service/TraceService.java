package com.vipshop.microscope.web.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vipshop.microscope.hbase.domain.App;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.query.core.QueryTemplate;
import com.vipshop.microscope.thrift.Annotation;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.TraceFactory;

public class TraceService {
	
	QueryTemplate template = new QueryTemplate();
	
	public List<Map<String, Object>> getQueryCondition() {
		TraceFactory.getTrace().clientSend("getQueryCondition");
		List<Map<String, Object>> conditions = new ArrayList<Map<String, Object>>();
		List<App> appIndexs = template.getAppIndex();
		for (App appIndex : appIndexs) {
			Map<String, Object> appAndTrace = new LinkedHashMap<String, Object>();
			String name = appIndex.getAppName();
			appAndTrace.put("app", name);
			appAndTrace.put("trace", template.getTraceNameByAppName(name));
			conditions.add(appAndTrace);
		}
		TraceFactory.getTrace().clientReceive();
		return conditions;
	}
	
	public List<Map<String, Object>> getTraceList(Map<String, String> query) {
		List<Map<String, Object>> traceLists = new ArrayList<Map<String, Object>>();
		List<TraceTable> tableTraces = template.getTraceListByTraceQuery(query);
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
			trace.put("serviceCounts", template.getSpanNameByTraceId(traceId));
			traceLists.add(trace);
		}
		return traceLists;
	}
	
	public Map<String, Object> getTraceSpan(String traceId) {
		Map<String, Object> traceSpan = new LinkedHashMap<String, Object>();

		traceSpan.put("traceId", traceId);
		List<Map<String, Object>> spans = new ArrayList<Map<String,Object>>();
		List<Span> spanTables = template.getSpanByTraceId(traceId);
		for (Span span : spanTables) {
			Map<String, Object> spanInfo = new LinkedHashMap<String, Object>();
			spanInfo.put("traceId", traceId);
			spanInfo.put("name", span.getName());
			spanInfo.put("id", span.getId());
			spanInfo.put("services", span.getName());
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
				annotationMap.put("value", annotation.getValue());
				if (annotation.getValue().equals("cs")) {
					startstmp = annotation.getTimestamp();
				}
				
				if (annotation.getValue().equals("cr")) {
					endstmp = annotation.getTimestamp();
				}
				annotationMap.put("host", annotation.getHost().getIpv4());
				annoInfo.add(annotationMap);
			}
			
			spanInfo.put("startTimestamp", startstmp);
			spanInfo.put("duration", endstmp - startstmp);
			spanInfo.put("annotations", annoInfo);
			spans.add(spanInfo);
		}
		
		traceSpan.put("spans", spans);
		
		TraceTable table = template.getTraceTableByTraceId(traceId);
		traceSpan.put("startTimestamp", table.getStartTimestamp());
		traceSpan.put("endTimestamp", table.getEndTimestamp());
		traceSpan.put("durationMicro", table.getDuration());
		traceSpan.put("serviceCounts", template.getSpanNameByTraceId(traceId));
		
		return traceSpan;
		
	}

}
