package com.vipshop.microscope.storage.hbase.factory;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Scan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.hbase.domain.AppTable;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.repository.AppTableRepository;
import com.vipshop.microscope.storage.hbase.repository.SpanTableRepository;
import com.vipshop.microscope.storage.hbase.repository.TraceTableRepository;

public class HbaseFactory {
	
	private static AppTableRepository APP;
	private static TraceTableRepository TRACE;
	private static SpanTableRepository SPAN;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-storage-hbase.xml", HbaseFactory.class);
		APP = context.getBean(AppTableRepository.class);
		TRACE = context.getBean(TraceTableRepository.class);
		SPAN = context.getBean(SpanTableRepository.class);
		synchronized (HbaseFactory.class) {
			APP.initialize();
			TRACE.initialize();
			SPAN.initialize();
		}
		context.close();
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
	
	public static AppTableRepository getAppTableRepository() {
		return APP;
	}
	
	public static TraceTableRepository getTraceTableRepository() {
		return TRACE;
	}
	
	public static SpanTableRepository getSpanTableRepository() {
		return SPAN;
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
	
	public static List<Map<String, Object>> findAppIPTrace() {
		return APP.findAppIPTrace();
	}
	
	public static List<TraceTable> find(Map<String, String> query) {
		return TRACE.find(query);
	}
	
	public static List<TraceTable> find(Scan scan) {
		return TRACE.find(scan);
	}
	
	public static List<Span> find(String traceId) {
		return SPAN.find(traceId);
	}

	public static Map<String, Integer> findSpanName(String traceId) {
		return SPAN.findSpanName(traceId);
	}
	
}
