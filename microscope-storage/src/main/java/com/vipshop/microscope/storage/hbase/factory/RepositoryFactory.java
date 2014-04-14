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

    // ************** system message ************************************************ //
    private static final SystemRepository SYSTEM;

    // ************** trace message ************************************************ //
    private static final TraceIndexRepository TRACE_INDEX;
    private static final TraceOverviewRepository TRACE_OVERVIEW;
    private static final TraceRepository TRACE;

    // ************** exception message ************************************************ //
    private static final ExceptionIndexRepository EXCEPTION_INDEX;
    private static final ExceptionRepository EXCEPTION;

    // ************** metrics message ************************************************ //
    private static final TSDBIndexRepository TSDB_INDEX;
    private static final TSDBUIDRepository TSDBUID;
    private static final TSDBRepository TSDB;

    // ************** report info ***************************************************** //
	private static final ReportTopRepository TOP;

    // ************** user info ******************************************************* //
	private static final UserRepository USER;

	static {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-storage-hbase.xml", RepositoryFactory.class);

        // ************************** initialize repository *************************************** //

        SYSTEM = context.getBean(SystemRepository.class);

		TRACE_INDEX   = context.getBean(TraceIndexRepository.class);
		TRACE_OVERVIEW = context.getBean(TraceOverviewRepository.class);
		TRACE  = context.getBean(TraceRepository.class);
		
		EXCEPTION_INDEX = context.getBean(ExceptionIndexRepository.class);
		EXCEPTION = context.getBean(ExceptionRepository.class);

        TSDB_INDEX = context.getBean(TSDBIndexRepository.class);
        TSDB = context.getBean(TSDBRepository.class);
        TSDBUID = context.getBean(TSDBUIDRepository.class);

        TOP = context.getBean(ReportTopRepository.class);

        USER = context.getBean(UserRepository.class);

        // ************************** initialize table ************************************************ //

        SYSTEM.initialize();

		TRACE_INDEX.initialize();
		TRACE_OVERVIEW.initialize();
		TRACE.initialize();
		
		EXCEPTION_INDEX.initialize();
		EXCEPTION.initialize();

        TSDB_INDEX.initialize();
        TSDB.initialize();
        TSDBUID.initialize();

        TOP.initialize();

        USER.initialize();

        context.close();
	}

    /**
     * Return {@link SystemRepository}
     *
     * @return
     */
    public static SystemRepository getSystemRepository() {
        return SYSTEM;
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
     * Return {@link TSDBRepository}
     *
     * @return
     */
    public static TSDBRepository getTsdbRepository() {
        return TSDB;
    }

    /**
     * Return {@link TSDBUIDRepository}
     *
     * @return
     */
    public static TSDBUIDRepository getTsdbuidRepository() {
        return TSDBUID;
    }

    /**
     * Return {@link TSDBIndexRepository}
     *
     * @return
     */
    public static TSDBIndexRepository getTsdbIndexReporsitory() {
        return TSDB_INDEX;
    }

	/**
	 * Return {@link com.vipshop.microscope.storage.hbase.repository.ReportTopRepository}
	 *
	 * @return
	 */
	public static ReportTopRepository getTopRepository() {
		return TOP;
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
