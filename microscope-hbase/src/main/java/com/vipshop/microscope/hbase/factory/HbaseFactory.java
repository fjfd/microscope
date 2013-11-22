package com.vipshop.microscope.hbase.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.hbase.repository.AppTraceRepository;
import com.vipshop.microscope.hbase.repository.SpanTableRepository;
import com.vipshop.microscope.hbase.repository.TraceTableRepository;

public class HbaseFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(HbaseFactory.class);
	
	public static AppTraceRepository APP_TRACE;
	public static TraceTableRepository TRACE;
	public static SpanTableRepository SPAN;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-hbase.xml", HbaseRepository.class);
		
		APP_TRACE = context.getBean(AppTraceRepository.class);
		TRACE = context.getBean(TraceTableRepository.class);
		SPAN = context.getBean(SpanTableRepository.class);
		
		synchronized (HbaseRepository.class) {
			logger.info("init hbase table app");
			HbaseFactory.APP_TRACE.initialize();
			
			logger.info("init hbase table trace");
			HbaseFactory.TRACE.initialize();
			
			logger.info("init hbase table span");
			HbaseFactory.SPAN.initialize();
		}
		
		context.close();
	}
	
}
