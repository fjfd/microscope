package com.vipshop.microscope.storage.hbase.factory;

import com.vipshop.microscope.storage.hbase.repository.*;
import org.apache.hadoop.conf.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
	
	private static final TopReportRepository TOP;

	private static final UserRepository USER;
	
	private static final TSDBRepository TSDB;
	private static final TSDBUIDRepository TSDBUID;
    private static final TSDBIndexRepository TSDB_INDEX;
	
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
		
		TOP = context.getBean(TopReportRepository.class);

		// ************** user   ********************************************** //
		
		USER = context.getBean(UserRepository.class);

        TSDB_INDEX = context.getBean(TSDBIndexRepository.class);
		TSDB = context.getBean(TSDBRepository.class);
		TSDBUID = context.getBean(TSDBUIDRepository.class);
		
		TRACE_INDEX.initialize();
		TRACE_OVERVIEW.initialize();
		TRACE.initialize();
		
		EXCEPTION_INDEX.initialize();
		EXCEPTION.initialize();
		
		TOP.initialize();

		USER.initialize();

        TSDB_INDEX.initialize();
		TSDB.initialize();
		TSDBUID.initialize();
		
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
	 * Return {@link TopReportRepository}
	 * 
	 * @return
	 */
	public static TopReportRepository getTopRepository() {
		return TOP;
	}

	/**
	 * Return {@link UserRepository}
	 * @return
	 */
	public static UserRepository getUserRepository() {
		return USER;
	}
	
	public static TSDBRepository getTsdbRepository() {
		return TSDB;
	}
	
	public static TSDBUIDRepository getTsdbuidRepository() {
		return TSDBUID;
	}

    public static TSDBIndexRepository getTsdbIndexReporsitory() {
        return TSDB_INDEX;
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
