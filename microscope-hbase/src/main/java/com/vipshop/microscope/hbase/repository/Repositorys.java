package com.vipshop.microscope.hbase.repository;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Repositorys {
	
	public static AppTraceRepository APP_TRACE;
	public static TraceTableRepository TRACE;
	public static SpanTableRepository SPAN;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-hbase.xml", Repositorys.class);
		
		APP_TRACE = context.getBean(AppTraceRepository.class);
		TRACE = context.getBean(TraceTableRepository.class);
		SPAN = context.getBean(SpanTableRepository.class);
		
		synchronized (Repositorys.class) {
			APP_TRACE.initialize();
			TRACE.initialize();
			SPAN.initialize();
		}
		
		context.close();
	}
	
	public static void init() {
		APP_TRACE.initialize();
		TRACE.initialize();
		SPAN.initialize();
	}
	
	public static void drop() {
		APP_TRACE.drop();
		TRACE.drop();
		SPAN.drop();
	}
	
}
