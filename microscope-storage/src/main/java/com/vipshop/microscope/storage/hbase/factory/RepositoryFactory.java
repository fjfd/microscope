package com.vipshop.microscope.storage.hbase.factory;

import org.apache.hadoop.conf.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.storage.hbase.repository.ExceptionIndexRepository;
import com.vipshop.microscope.storage.hbase.repository.ExceptionRepository;
import com.vipshop.microscope.storage.hbase.repository.JVMReportRepository;
import com.vipshop.microscope.storage.hbase.repository.ReportIndexRepository;
import com.vipshop.microscope.storage.hbase.repository.ServletReportRepository;
import com.vipshop.microscope.storage.hbase.repository.TopReportRepository;
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
	
	private static final ReportIndexRepository REPORT_INDEX;
	private static final TopReportRepository TOP;
	private static final JVMReportRepository JVM;
	private static final ServletReportRepository SERVLET;
	
	private static final UserRepository USER;
	
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
		
		REPORT_INDEX = context.getBean(ReportIndexRepository.class);
		TOP = context.getBean(TopReportRepository.class);
		JVM = context.getBean(JVMReportRepository.class);
		SERVLET = context.getBean(ServletReportRepository.class);

		// ************** user   ********************************************** //
		
		USER = context.getBean(UserRepository.class);
		
		TRACE_INDEX.initialize();
		TRACE_OVERVIEW.initialize();
		TRACE.initialize();
		
		EXCEPTION_INDEX.initialize();
		EXCEPTION.initialize();
		
		REPORT_INDEX.initialize();
		TOP.initialize();
		JVM.initialize();
		SERVLET.initialize();

		USER.initialize();
		
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
	 * Return {@link ReportIndexRepository}
	 * 
	 * @return
	 */
	public static ReportIndexRepository getReportIndexRepository() {
		return REPORT_INDEX;
	}
	
	/**
	 * Return {@link TopReportRepository}
	 * 
	 * @return
	 */
	public static TopReportRepository getTopRepository() {
		return TOP;
	}

	/**
	 * Return {@link JVMReportRepository}
	 * 
	 * @return
	 */
	public static JVMReportRepository getJVMRepository() {
		return JVM;
	}
	
	/**
	 * 
	 * @return
	 */
	public static ServletReportRepository getServletRepository() {
		return SERVLET;
	}

	/**
	 * Return {@link UserRepository}
	 * @return
	 */
	public static UserRepository getUserRepository() {
		return USER;
	}

	/**
	 * Return Configuration
	 * 
	 * @return
	 */
	public static Configuration getConfiguration() {
		return USER.getConfiguration();
	}
	
}
