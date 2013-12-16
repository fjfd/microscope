package com.vipshop.microscope.storage.hbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class HbaseFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(HbaseFactory.class);
	
	public static AppTableRepository APP;
	public static TraceTableRepository TRACE;
	public static SpanTableRepository SPAN;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-hbase.xml", HbaseRepository.class);
		
		APP = context.getBean(AppTableRepository.class);
		TRACE = context.getBean(TraceTableRepository.class);
		SPAN = context.getBean(SpanTableRepository.class);
		
		synchronized (HbaseRepository.class) {
			logger.info("init hbase table app");
			HbaseFactory.APP.initialize();
			
			logger.info("init hbase table trace");
			HbaseFactory.TRACE.initialize();
			
			logger.info("init hbase table span");
			HbaseFactory.SPAN.initialize();
		}
		
		context.close();
	}
	
}
