package com.vipshop.microscope.storage.hbase;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.storage.domain.AppTable;
import com.vipshop.microscope.storage.domain.TraceTable;
import com.vipshop.microscope.thrift.gen.Span;

public class HbaseRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(HbaseRepository.class);
	
	public static void save(AppTable appTable) {
		logger.debug("save AppTable to hbase " + appTable);
		HbaseFactory.APP.save(appTable);
	}
	
	public static void save(TraceTable traceTable) {
		logger.debug("save TraceTable to hbase " + traceTable);
		HbaseFactory.TRACE.save(traceTable);
	}
	
	public static void save(Span span) {
		logger.debug("save Span to hbase " + span);
		HbaseFactory.SPAN.save(span);
	}
	
	public static List<Map<String, Object>> findAll() {
		logger.debug("find AppTable from hbase ");
		return HbaseFactory.APP.findAll();
	}
	
	public static List<TraceTable> findByQuery(Map<String, String> query) {
		logger.debug("find trace list from hbase ");
		return HbaseFactory.TRACE.findByQuery(query);
	}
	
	public static Map<String, Integer> findSpanNameByTraceId(String traceId) {
		logger.debug("find span names by trace id " + traceId);
		return HbaseFactory.SPAN.findSpanNameByTraceId(traceId);
	}
	
	public static List<Span> findSpanByTraceId(String traceId) {
		logger.debug("find span list by trace id " + traceId);
		return HbaseFactory.SPAN.findSpanByTraceId(traceId);
	}
	
	public static List<TraceTable> findByTraceId(String traceId) {
		logger.debug("find trace by trace id " + traceId);
		return HbaseFactory.TRACE.findByTraceId(traceId);
	}
	
	public static void reinit() {
		drop();
		init();
	}
	
	public static void init() {
		logger.info("init hbase table app");
		HbaseFactory.APP.initialize();
		
		logger.info("init hbase table trace");
		HbaseFactory.TRACE.initialize();
		
		logger.info("init hbase table span");
		HbaseFactory.SPAN.initialize();
		
	}
	
	public static void drop() {
		logger.info("drop hbase table app");
		HbaseFactory.APP.drop();
		
		logger.info("drop hbase table trace");
		HbaseFactory.TRACE.drop();
		
		logger.info("drop hbase table span");
		HbaseFactory.SPAN.drop();
		
	}
}
