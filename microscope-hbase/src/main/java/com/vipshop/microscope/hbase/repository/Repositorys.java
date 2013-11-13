package com.vipshop.microscope.hbase.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Repositorys {
	
	private static final Logger logger = LoggerFactory.getLogger(Repositorys.class);
	
	public static AppTraceRepository APP_TRACE;
	public static TraceTableRepository TRACE;
	public static SpanTableRepository SPAN;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-hbase.xml", Repositorys.class);
		
		APP_TRACE = context.getBean(AppTraceRepository.class);
		TRACE = context.getBean(TraceTableRepository.class);
		SPAN = context.getBean(SpanTableRepository.class);
		
		synchronized (Repositorys.class) {
			init();
		}
		
		context.close();
	}
	
	public static void init() {
		logger.info("init hbase table app");
		APP_TRACE.initialize();
		
		logger.info("init hbase table trace");
		TRACE.initialize();
		
		logger.info("init hbase table span");
		SPAN.initialize();
		
	}
	
	public static void drop() {
		logger.info("drop hbase table app");
		APP_TRACE.drop();
		
		logger.info("drop hbase table trace");
		TRACE.drop();
		
		logger.info("drop hbase table span");
		SPAN.drop();
		
	}
	
}
