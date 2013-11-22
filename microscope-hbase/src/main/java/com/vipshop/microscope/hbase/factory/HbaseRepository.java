package com.vipshop.microscope.hbase.factory;

import java.util.List;
import java.util.Map;

import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.thrift.Span;

public class HbaseRepository {
	
	public static void save(AppTrace appIndex) {
		HbaseFactory.APP_TRACE.save(appIndex);
	}
	
	public static void save(TraceTable traceTable) {
		HbaseFactory.TRACE.save(traceTable);
	}
	
	public static void save(Span span) {
		HbaseFactory.SPAN.save(span);
	}
	
	public static List<Map<String, Object>> findAll() {
		return HbaseFactory.APP_TRACE.findAll();
	}
	
	public static List<TraceTable> findByQuery(Map<String, String> query) {
		return HbaseFactory.TRACE.findByQuery(query);
	}
	
	public static Map<String, String> findSpanNameByTraceId(String traceId) {
		return HbaseFactory.SPAN.findSpanNameByTraceId(traceId);
	}
	
	public static List<Span> findSpanByTraceId(String traceId) {
		return HbaseFactory.SPAN.findSpanByTraceId(traceId);
	}
	
	public static TraceTable findByTraceId(String traceId) {
		return HbaseFactory.TRACE.findByTraceId(traceId);
	}
}
