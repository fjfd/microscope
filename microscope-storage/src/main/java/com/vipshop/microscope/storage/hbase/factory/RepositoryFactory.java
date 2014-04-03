package com.vipshop.microscope.storage.hbase.factory;

import org.apache.hadoop.conf.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.storage.hbase.repository.ExceptionIndexRepository;
import com.vipshop.microscope.storage.hbase.repository.ExceptionRepository;
import com.vipshop.microscope.storage.hbase.repository.JVMRepository;
import com.vipshop.microscope.storage.hbase.repository.ServletRepository;
import com.vipshop.microscope.storage.hbase.repository.TopRepository;
import com.vipshop.microscope.storage.hbase.repository.TraceIndexRepository;
import com.vipshop.microscope.storage.hbase.repository.TraceOverviewRepository;
import com.vipshop.microscope.storage.hbase.repository.TraceRepository;
import com.vipshop.microscope.storage.hbase.repository.UserRepository;

/**
 * Hbase Factory responsible for create Repository.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class RepositoryFactory {
	
	private static final TraceIndexRepository TRACE_INDEX;
	private static final TraceOverviewRepository TRACE_OVERVIEW;
	private static final TraceRepository TRACE;
	
	private static final ExceptionIndexRepository EXCEPTION_INDEX;
	private static final ExceptionRepository EXCEPTION;
	
	private static final JVMRepository JVM_TABLB;
	private static final ServletRepository SERVLET_TABLB;
	
	private static final TopRepository TOP_TABLE;
	
	private static final UserRepository USER_TABLE;
	
	/**
	 * Initialize hbase tables. 
	 */
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-storage-hbase.xml", RepositoryFactory.class);
		
		// ************** trace ************************************************ //
		
		TRACE_INDEX   = context.getBean(TraceIndexRepository.class);
		TRACE_OVERVIEW = context.getBean(TraceOverviewRepository.class);
		TRACE  = context.getBean(TraceRepository.class);
		
		// ************** exception ******************************************** //
		
		EXCEPTION_INDEX = context.getBean(ExceptionIndexRepository.class);
		EXCEPTION = context.getBean(ExceptionRepository.class);
		
		// ************** report **********************************************  //
		
		JVM_TABLB = context.getBean(JVMRepository.class);
		TOP_TABLE = context.getBean(TopRepository.class);
		SERVLET_TABLB = context.getBean(ServletRepository.class);

		// ************** user   ********************************************** //
		
		USER_TABLE = context.getBean(UserRepository.class);
		
		TRACE_INDEX.initialize();
		TRACE_OVERVIEW.initialize();
		TRACE.initialize();
		
		EXCEPTION_INDEX.initialize();
		EXCEPTION.initialize();
		
		TOP_TABLE.initialize();
		JVM_TABLB.initialize();
		USER_TABLE.initialize();
		SERVLET_TABLB.initialize();
		
		context.close();
	}
	
	/**
	 * Return {@link TraceIndexRepository}
	 * 
	 * @return
	 */
	public static TraceIndexRepository getTraceIndexRepository() {
		return TRACE_INDEX;
	}
	
	/**
	 * Return {@link TraceOverviewRepository}
	 * 
	 * @return
	 */
	public static TraceOverviewRepository getTraceOverviewRepository() {
		return TRACE_OVERVIEW;
	}
	
	/**
	 * Return {@link TraceRepository}
	 * 
	 * @return
	 */
	public static TraceRepository getTraceRepository() {
		return TRACE;
	}
	
	/**
	 * Return {@link ExceptionIndexRepository}
	 * 
	 * @return
	 */
	public static ExceptionIndexRepository getExceptionIndexRepository() {
		return EXCEPTION_INDEX;
	}
	
	/**
	 * Return {@link ExceptionRepository}
	 * 
	 * @return
	 */
	public static ExceptionRepository getExceptionRepository() {
		return EXCEPTION;
	}
	
	/**
	 * Return {@link JVMRepository}
	 * 
	 * @return
	 */
	public static JVMRepository getJVMTableRepository() {
		return JVM_TABLB;
	}
	
	/**
	 * Return {@link TopRepository}
	 * 
	 * @return
	 */
	public static TopRepository getTopTableRepository() {
		return TOP_TABLE;
	}
	
	/**
	 * Return {@link UserRepository}
	 * @return
	 */
	public static UserRepository getUserTableRepository() {
		return USER_TABLE;
	}
	
	/**
	 * 
	 * @return
	 */
	public static ServletRepository getServletTableRepository() {
		return SERVLET_TABLB;
	}
	
	/**
	 * Return Configuration
	 * 
	 * @return
	 */
	public static Configuration getConfiguration() {
		return USER_TABLE.getConfiguration();
	}
	
}
