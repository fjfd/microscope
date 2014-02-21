package com.vipshop.microscope.storage.hbase;

import java.util.List;
import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.domain.AppTable;
import com.vipshop.microscope.storage.domain.TraceTable;

public class HbaseRepository {
	
	private static AppTableRepository APP;
	private static TraceTableRepository TRACE;
	private static SpanTableRepository SPAN;
	
	static {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-storage-hbase.xml", HbaseRepository.class);
		
		APP = context.getBean(AppTableRepository.class);
		TRACE = context.getBean(TraceTableRepository.class);
		SPAN = context.getBean(SpanTableRepository.class);
		
		synchronized (HbaseRepository.class) {
			APP.initialize();
			TRACE.initialize();
			SPAN.initialize();
		}
		
		context.close();
	}
	
	public static void save(AppTable appTable) {
		APP.save(appTable);
	}
	
	public static void save(TraceTable traceTable) {
		TRACE.save(traceTable);
	}
	
	public static void save(Span span) {
		SPAN.save(span);
	}
	
	public static List<Map<String, Object>> findAll() {
		return APP.findAll();
	}
	
	public static List<String> findApps() {
		return APP.findApps();
	}
	
	public static List<TraceTable> findByQuery() {
		return TRACE.findByQuery();
	}
	
	public static List<TraceTable> findByQuery(Map<String, String> query) {
		return TRACE.findByQuery(query);
	}
	
	public static Map<String, Integer> findSpanNameByTraceId(String traceId) {
		return SPAN.findSpanNameByTraceId(traceId);
	}
	
	public static List<Span> findSpanByTraceId(String traceId) {
		return SPAN.findSpanByTraceId(traceId);
	}
	
	public static List<TraceTable> findByTraceId(String traceId) {
		return TRACE.findByTraceId(traceId);
	}
	
	public static void reinit() {
		drop();
		init();
	}
	
	public static void init() {
		APP.initialize();
		TRACE.initialize();
		SPAN.initialize();
	}
	
	public static void drop() {
		APP.drop();
		TRACE.drop();
		SPAN.drop();
	}
}
