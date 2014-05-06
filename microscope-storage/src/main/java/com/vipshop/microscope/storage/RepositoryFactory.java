package com.vipshop.microscope.storage;

import org.apache.hadoop.conf.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * HBase Factory responsible for create Repository.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class RepositoryFactory {

    private static final SystemRepository SYSTEM;
    private static final TraceIndexRepository TRACE_INDEX;
    private static final TraceOverviewRepository TRACE_OVERVIEW;
    private static final TraceRepository TRACE;
    private static final ExceptionRepository EXCEPTION;
    private static final MetricRepository METRIC;
    private static final ReportRepository REPORT;
    private static final LogRepository LOG;
    private static final AlertRepository ALERT;
    private static final UserRepository USER;

    static {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-storage-hbase.xml",
                                                                                RepositoryFactory.class);
        SYSTEM = context.getBean(SystemRepository.class);
        TRACE_INDEX = context.getBean(TraceIndexRepository.class);
        TRACE_OVERVIEW = context.getBean(TraceOverviewRepository.class);
        TRACE = context.getBean(TraceRepository.class);
        EXCEPTION = context.getBean(ExceptionRepository.class);
        METRIC = context.getBean(MetricRepository.class);
        REPORT = context.getBean(ReportRepository.class);
        LOG = context.getBean(LogRepository.class);
        ALERT = context.getBean(AlertRepository.class);
        USER = context.getBean(UserRepository.class);

        SYSTEM.create();
        TRACE_INDEX.create();
        TRACE_OVERVIEW.create();
        TRACE.create();
        EXCEPTION.create();
        METRIC.create();
        REPORT.create();
        LOG.create();
        ALERT.create();
        USER.create();

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
     * Return {@link ExceptionRepository}
     *
     * @return
     */
    public static ExceptionRepository getExceptionRepository() {
        return EXCEPTION;
    }

    /**
     * Return {@link MetricRepository}
     *
     * @return
     */
    public static MetricRepository getMetricRepository() {
        return METRIC;
    }

    /**
     * Return {@link UserRepository}
     *
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
