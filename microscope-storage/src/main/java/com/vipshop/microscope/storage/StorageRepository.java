package com.vipshop.microscope.storage;

import com.vipshop.microscope.storage.hbase.HBaseRepository;
import com.vipshop.microscope.storage.hbase.TraceIndexTable;
import com.vipshop.microscope.storage.hbase.TraceOverviewTable;
import com.vipshop.microscope.storage.tsdb.TSDBRepository;
import com.vipshop.microscope.storage.opentsdb.core.Aggregator;
import com.vipshop.microscope.storage.opentsdb.core.DataPoints;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;
import org.apache.hadoop.hbase.client.Scan;

import java.util.List;
import java.util.Map;

/**
 * Storage API.
 *
 * Storage API responsible for store and query data.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class StorageRepository {

    private final HBaseRepository hbaseRepository = new HBaseRepository();

    private final TSDBRepository openTSDBRepository = new TSDBRepository();

    public static StorageRepository getStorageRepository() {
        return StorageRepositoryHolder.storageRepository;
    }

    public void createHbaseTable() {
        hbaseRepository.create();
    }

    public void dropHbaseTable() {
        hbaseRepository.drop();
    }

    public void initHbaseTable() {
        hbaseRepository.init();
    }

    public void save(SystemData info) {
        hbaseRepository.save(info);
    }

    public void save(TraceIndexTable appTable) {
        hbaseRepository.save(appTable);
    }

    public void save(TraceOverviewTable traceTable) {
        hbaseRepository.save(traceTable);
    }

    public void save(Span span) {
        hbaseRepository.save(span);
    }

    public void saveExceptionIndex(Map<String, Object> exception) {
        hbaseRepository.saveExceptionIndex(exception);
    }

    public void saveException(Map<String, Object> exception) {
        hbaseRepository.saveException(exception);
    }

    public void saveTop(Map<String, Object> top) {
        hbaseRepository.saveTop(top);
    }

    public void saveUser(Map<String, String> user) {
        hbaseRepository.saveUser(user);
    }

    public void add(final String metric, final long timestamp, final long value, final Map<String, String> tags) {
        openTSDBRepository.add(metric, timestamp, value, tags);
    }

    public void add(final String metric, final long timestamp, final double value, final Map<String, String> tags) {
        openTSDBRepository.add(metric, timestamp, value, tags);
    }

    public void add(final String metric, final long timestamp, final float value, final Map<String, String> tags) {
        openTSDBRepository.add(metric, timestamp, value, tags);
    }

    public void add(final String metric, final long timestamp, final boolean value, final Map<String, String> tags) {
        long tmp = value == true ? 1l : 0l;
        openTSDBRepository.add(metric, timestamp, tmp, tags);
    }

    public DataPoints[] find(long starttimestamp,
                             String metric, Map<String, String> tags,
                             Aggregator function, boolean rate) {
        return openTSDBRepository.find(starttimestamp, System.currentTimeMillis(), metric, tags, function, rate);
    }

    public DataPoints[] find(long starttimestamp, long endtimestamp,
                             String metric, Map<String, String> tags,
                             Aggregator function, boolean rate) {
        return openTSDBRepository.find(starttimestamp, endtimestamp, metric, tags, function, rate);
    }

    public List<String> suggestMetrics(final String search) {
        return openTSDBRepository.suggestMetrics(search);
    }

    public List<String> suggestTagNames(final String search) {
        return openTSDBRepository.suggestTagNames(search);
    }

    public List<String> suggestTagValues(final String search) {
        return openTSDBRepository.suggestTagValues(search);
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

    private static class StorageRepositoryHolder {
        public static StorageRepository storageRepository = new StorageRepository();
    }


}
