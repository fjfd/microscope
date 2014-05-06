package com.vipshop.microscope.storage;

import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;
import net.opentsdb.core.Aggregator;
import net.opentsdb.core.DataPoints;

import java.util.List;
import java.util.Map;

/**
 * Storage API for monitor platform.
 * <p/>
 * Storage API responsible for store and query data.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class StorageRepository {

    private static class StorageRepositoryHolder {
        public static StorageRepository storageRepository = new StorageRepository();
    }

    public static StorageRepository getStorageRepository() {
        return StorageRepositoryHolder.storageRepository;
    }

    /**
     * Create table
     */
    public void createHBaseTable() {
        RepositoryFactory.getSystemRepository().create();
        RepositoryFactory.getTraceIndexRepository().create();
        RepositoryFactory.getTraceOverviewRepository().create();
        RepositoryFactory.getTraceRepository().create();
        RepositoryFactory.getExceptionRepository().create();
        RepositoryFactory.getMetricRepository().create();
        RepositoryFactory.getUserRepository().create();
    }

    /**
     * Drop table
     */
    public void dropHBaseTable() {
        RepositoryFactory.getSystemRepository().drop();
        RepositoryFactory.getTraceIndexRepository().drop();
        RepositoryFactory.getTraceOverviewRepository().drop();
        RepositoryFactory.getTraceRepository().drop();
        RepositoryFactory.getExceptionRepository().drop();
        RepositoryFactory.getMetricRepository().drop();
        RepositoryFactory.getUserRepository().drop();
    }

    /**
     * Drop table then create table
     */
    public void initHBaseTable() {
        dropHBaseTable();
        createHBaseTable();
    }

    /**
     * Save trace index data
     *
     * @param appTable
     */
    public void save(TraceIndexTable appTable) {
        RepositoryFactory.getTraceIndexRepository().save(appTable);
    }

    /**
     * Save trace overview data
     *
     * @param traceTable
     */
    public void save(TraceOverviewTable traceTable) {
        RepositoryFactory.getTraceOverviewRepository().save(traceTable);
    }

    /**
     * Save trace data
     *
     * @param span
     */
    public void save(Span span) {
        RepositoryFactory.getTraceRepository().save(span);
    }

    /**
     * Save system data
     *
     * @param systemData
     */
    public void save(SystemData systemData) {
        RepositoryFactory.getSystemRepository().save(systemData);
    }

    /**
     * Save exception data
     *
     * @param exception
     */
    public void save(ExceptionData exception) {
        RepositoryFactory.getExceptionRepository().save(exception);
    }

    /**
     * Save metric data
     *
     * @param metric the data of metric
     */
    public void save(MetricData metric) {
        RepositoryFactory.getMetricRepository().save(metric);
    }

    public List<Map<String, Object>> findExceptionIndex() {
        return RepositoryFactory.getExceptionRepository().find();
    }

    public List<ExceptionData> findExceptionData(Map<String, String> query) {
        return RepositoryFactory.getExceptionRepository().find(query);
    }

    public DataPoints[] find(long start,
                             String metric, Map<String, String> tags,
                             Aggregator function, boolean rate) {
        return RepositoryFactory.getMetricRepository().find(start, metric, tags, function, rate);
    }

    public DataPoints[] find(long start, long end,
                             String metric, Map<String, String> tags,
                             Aggregator function, boolean rate) {
        return RepositoryFactory.getMetricRepository().find(start, end, metric, tags, function, rate);
    }

    public List<Map<String, Object>> findSystemIndex() {
        return null;
    }

    public SystemData findSystemData(Map<String, String> query) {
        return RepositoryFactory.getSystemRepository().find(query);
    }

    public List<Map<String, Object>> findTraceIndex() {
        return RepositoryFactory.getTraceIndexRepository().find();
    }

    public List<TraceOverviewTable> findTraceList(Map<String, String> query) {
        return RepositoryFactory.getTraceOverviewRepository().find(query);
    }

    public Map<String, Integer> findSpanName(String traceId) {
        return RepositoryFactory.getTraceRepository().findSpanName(traceId);
    }

    public List<Span> findTrace(String traceId) {
        return RepositoryFactory.getTraceRepository().find(traceId);
    }

}
