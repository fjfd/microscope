package com.vipshop.microscope.storage;

import com.vipshop.microscope.storage.hbase.HBaseRepository;
import com.vipshop.microscope.storage.hbase.TraceIndexTable;
import com.vipshop.microscope.storage.hbase.TraceOverviewTable;
import com.vipshop.microscope.storage.tsdb.TSDBRepository;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;
import net.opentsdb.core.Aggregator;
import net.opentsdb.core.DataPoints;
import org.apache.hadoop.hbase.client.Scan;

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

    private final HBaseRepository hbaseRepository = new HBaseRepository();

    private final TSDBRepository TSDBRepository = new TSDBRepository();

    private static class StorageRepositoryHolder {
        public static StorageRepository storageRepository = new StorageRepository();
    }

    public static StorageRepository getStorageRepository() {
        return StorageRepositoryHolder.storageRepository;
    }

    // ************************** table operations **************************************** //

    /**
     * Create table
     */
    public void createHBaseTable() {
        hbaseRepository.create();
    }

    /**
     * Drop table
     */
    public void dropHBaseTable() {
        hbaseRepository.drop();
    }

    /**
     * Drop table then create table
     */
    public void initHBaseTable() {
        hbaseRepository.init();
    }

    // ************************* write operations ************************************** //

    /**
     * Save trace index data
     *
     * @param appTable
     */
    public void save(TraceIndexTable appTable) {
        hbaseRepository.save(appTable);
    }

    /**
     * Save trace overview data
     *
     * @param traceTable
     */
    public void save(TraceOverviewTable traceTable) {
        hbaseRepository.save(traceTable);
    }

    /**
     * Save trace data
     *
     * @param span
     */
    public void save(Span span) {
        hbaseRepository.save(span);
    }

    /**
     * Save system data
     *
     * @param systemData
     */
    public void save(SystemData systemData) {
        hbaseRepository.save(systemData);
    }

    /**
     * Save exception data
     *
     * @param exception
     */
    public void save(ExceptionData exception) {
        // TODO
    }

    /**
     * Save metric data
     *
     * @param metric    the name of metric
     * @param timestamp the value of timestamp
     * @param value     the value of metric
     * @param tags      the tags of metric
     */
    public void save(final String metric, final long timestamp, final Object value, final Map<String, String> tags) {
        TSDBRepository.add(metric, timestamp, value, tags);
    }

    public void saveUser(Map<String, String> user) {
        hbaseRepository.saveUser(user);
    }

    public void saveTop(Map<String, Object> top) {
        hbaseRepository.saveTop(top);
    }


    // **************************** read operations ************************************** //


    public DataPoints[] find(long start,
                             String metric, Map<String, String> tags,
                             Aggregator function, boolean rate) {
        return TSDBRepository.find(start, System.currentTimeMillis(), metric, tags, function, rate);
    }

    public DataPoints[] find(long start, long end,
                             String metric, Map<String, String> tags,
                             Aggregator function, boolean rate) {
        return TSDBRepository.find(start, end, metric, tags, function, rate);
    }

    public List<String> suggestMetrics(final String search) {
        return TSDBRepository.suggestMetrics(search);
    }

    public List<String> suggestTagNames(final String search) {
        return TSDBRepository.suggestTagNames(search);
    }

    public List<String> suggestTagValues(final String search) {
        return TSDBRepository.suggestTagValues(search);
    }

    public SystemData getSystemInfo(Map<String, String> query) {
        return hbaseRepository.getSystemInfo(query);
    }

    public List<Map<String, Object>> findTraceIndex() {
        return hbaseRepository.findTraceIndex();
    }

    public List<TraceOverviewTable> findTraceList(Map<String, String> query) {
        return hbaseRepository.findTraceList(query);
    }

    public List<TraceOverviewTable> findTraceList(Scan scan) {
        return hbaseRepository.findTraceList(scan);
    }

    public List<Span> findTrace(String traceId) {
        return hbaseRepository.findTrace(traceId);
    }

    public Map<String, Integer> findSpanName(String traceId) {
        return hbaseRepository.findSpanName(traceId);
    }

    public List<Map<String, Object>> findExceptionIndex() {
        return hbaseRepository.findExceptionIndex();
    }

    public List<Map<String, Object>> findExceptionList(Map<String, String> query) {
        return hbaseRepository.findExceptionList(query);
    }

    public Map<String, Object> findTopList() {
        return hbaseRepository.findTopList();
    }

    public void saveMetricIndex(MetricData metric) {
        hbaseRepository.saveMetricIndex(metric);
    }

    public void saveMetric(MetricData metric) {
        hbaseRepository.saveMetric(metric);
    }

    public List<Map<String, Object>> findMetricIndex() {
        return hbaseRepository.findMetricsIndex();
    }

    public Map<String, Object> findName1(final String app, final String name1) {
        return hbaseRepository.findName1(app, name1);
    }

    public Map<String, Object> findName2(final String app, final String name2) {
        return hbaseRepository.findName2(app, name2);
    }

    public List<Map<String, Object>> findMetric(Map<String, String> query) {
        return hbaseRepository.findMetrics(query);
    }


}
