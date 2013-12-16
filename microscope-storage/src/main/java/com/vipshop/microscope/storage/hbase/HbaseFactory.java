package com.vipshop.microscope.storage.hbase;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Initialize Hbase Repositorys
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class HbaseFactory {
	
	public static AppTableRepository APP;
	public static TraceTableRepository TRACE;
	public static SpanTableRepository SPAN;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-storage-hbase.xml", HbaseRepository.class);
		
		APP = context.getBean(AppTableRepository.class);
		TRACE = context.getBean(TraceTableRepository.class);
		SPAN = context.getBean(SpanTableRepository.class);
		
		synchronized (HbaseRepository.class) {
			HbaseFactory.APP.initialize();
			HbaseFactory.TRACE.initialize();
			HbaseFactory.SPAN.initialize();
		}
		
		context.close();
	}
	
}
