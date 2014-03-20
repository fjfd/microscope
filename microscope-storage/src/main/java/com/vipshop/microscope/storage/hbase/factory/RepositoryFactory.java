package com.vipshop.microscope.storage.hbase.factory;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.storage.hbase.repository.AppTableRepository;
import com.vipshop.microscope.storage.hbase.repository.ExceptionTableRepository;
import com.vipshop.microscope.storage.hbase.repository.JVMTableRepository;
import com.vipshop.microscope.storage.hbase.repository.SpanTableRepository;
import com.vipshop.microscope.storage.hbase.repository.TraceTableRepository;

/**
 * Hbase Factory responsible for create Repository.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class RepositoryFactory {
	
	private static final AppTableRepository   APP_TABLE;
	private static final TraceTableRepository TRACE_TABLE;
	private static final SpanTableRepository  SPAN_TABLE;
	
	private static final ExceptionTableRepository EXCEPTION_TABLE;
	private static final JVMTableRepository JVM_TABLB;
	
	/**
	 * Initialize hbase tables. 
	 */
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-storage-hbase.xml", RepositoryFactory.class);
		APP_TABLE   = context.getBean(AppTableRepository.class);
		TRACE_TABLE = context.getBean(TraceTableRepository.class);
		SPAN_TABLE  = context.getBean(SpanTableRepository.class);
		EXCEPTION_TABLE = context.getBean(ExceptionTableRepository.class);
		JVM_TABLB = context.getBean(JVMTableRepository.class);
		
		APP_TABLE.initialize();
		TRACE_TABLE.initialize();
		SPAN_TABLE.initialize();
		
		EXCEPTION_TABLE.initialize();
		JVM_TABLB.initialize();
		
		context.close();
	}
	
	/**
	 * Return {@link AppTableRepository}
	 * 
	 * @return
	 */
	public static AppTableRepository getAppTableRepository() {
		return APP_TABLE;
	}
	
	/**
	 * Return {@link TraceTableRepository}
	 * 
	 * @return
	 */
	public static TraceTableRepository getTraceTableRepository() {
		return TRACE_TABLE;
	}
	
	/**
	 * Return {@link SpanTableRepository}
	 * 
	 * @return
	 */
	public static SpanTableRepository getSpanTableRepository() {
		return SPAN_TABLE;
	}
	
	/**
	 * Return {@link ExceptionTableRepository}
	 * 
	 * @return
	 */
	public static ExceptionTableRepository getExceptionTableRepository() {
		return EXCEPTION_TABLE;
	}
	
	/**
	 * Return {@link JVMTableRepository}
	 * 
	 * @return
	 */
	public static JVMTableRepository getJVMTableRepository() {
		return JVM_TABLB;
	}
	
}
