package com.vipshop.microscope.hbase.factory;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.thrift.Span;

public class HbaseRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(HbaseRepository.class);
	
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
	
	public static void init() {
		logger.info("init hbase table app");
		HbaseFactory.APP_TRACE.initialize();
		
		logger.info("init hbase table trace");
		HbaseFactory.TRACE.initialize();
		
		logger.info("init hbase table span");
		HbaseFactory.SPAN.initialize();
		
	}
	
	public static void drop() {
		logger.info("drop hbase table app");
		HbaseFactory.APP_TRACE.drop();
		
		logger.info("drop hbase table trace");
		HbaseFactory.TRACE.drop();
		
		logger.info("drop hbase table span");
		HbaseFactory.SPAN.drop();
		
	}
}
