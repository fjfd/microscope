package com.vipshop.microscope.storage.hbase;

import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;
import org.apache.hadoop.hbase.client.Scan;

import java.util.List;
import java.util.Map;

/**
 * HBase Storage API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class HBaseRepository {

    /**
     * Create HBase tables.
     */
    public void create() {

        RepositoryFactory.getSystemRepository().initialize();

        RepositoryFactory.getTraceIndexRepository().initialize();
        RepositoryFactory.getTraceOverviewRepository().initialize();
        RepositoryFactory.getTraceRepository().initialize();

        RepositoryFactory.getExceptionIndexRepository().initialize();
        RepositoryFactory.getExceptionRepository().initialize();

        RepositoryFactory.getTsdbRepository().initialize();
        RepositoryFactory.getTsdbuidRepository().initialize();
        RepositoryFactory.getTsdbIndexReporsitory().initialize();

        RepositoryFactory.getTopRepository().initialize();

        RepositoryFactory.getUserRepository().initialize();
    }

    /**
     * Drop hbase tables.
     */
    public void drop() {

        RepositoryFactory.getSystemRepository().drop();

        RepositoryFactory.getTraceIndexRepository().drop();
        RepositoryFactory.getTraceOverviewRepository().drop();
        RepositoryFactory.getTraceRepository().drop();

        RepositoryFactory.getExceptionIndexRepository().drop();
        RepositoryFactory.getExceptionRepository().drop();

        RepositoryFactory.getTsdbRepository().drop();
        RepositoryFactory.getTsdbuidRepository().drop();
        RepositoryFactory.getTsdbIndexReporsitory().drop();

        RepositoryFactory.getTopRepository().drop();

        RepositoryFactory.getUserRepository().drop();
    }

    /**
     * Drop hbast tables then create tables.
     */
    public void init() {
        this.drop();
        this.create();
    }

    /**
     * Store to table 'system'.
     *
     * @param info
     */
    public void save(SystemData info) {
        RepositoryFactory.getSystemRepository().save(info);
    }

    /**
     * Store {@link TraceIndexTable} to table 'trace_index'.
     *
     * @param appTable
     */
    public void save(TraceIndexTable appTable) {
        RepositoryFactory.getTraceIndexRepository().save(appTable);
    }

    /**
     * Store {@link TraceOverviewTable} to hbase.
     *
     * @param traceTable
     */
    public void save(TraceOverviewTable traceTable) {
        RepositoryFactory.getTraceOverviewRepository().save(traceTable);
    }

    /**
     * Store {@link Span} to hbase.
     *
     * @param span
     */
    public void save(Span span) {
        RepositoryFactory.getTraceRepository().save(span);
    }

    /**
     * Store exception index.
     *
     * @param
     */
    public void saveExceptionIndex(Map<String, Object> exception) {
        RepositoryFactory.getExceptionIndexRepository().save(exception);
    }


    /**
     * Store Map ojbect to exception table.
     *
     * @param
     */
    public void saveException(Map<String, Object> excption) {
        RepositoryFactory.getExceptionRepository().save(excption);
    }

    /**
     * Store top report to top table.
     *
     * @param top
     */
    public void saveTop(Map<String, Object> top) {
        RepositoryFactory.getTopRepository().save(top);
    }

    /**
     * Store user query info to hbase.
     *
     * @param user
     */
    public void saveUser(Map<String, String> user) {
        RepositoryFactory.getUserRepository().save(user);
    }

    // ******************************************************************************** //

    /**
     * Get by APP and IP
     *
     * @param query
     * @return
     */
    public SystemData getSystemInfo(Map<String, String> query) {
        return RepositoryFactory.getSystemRepository().find(query);
    }

    /**
     * Trace query index.
     *
     * @return
     */
    public List<Map<String, Object>> findTraceIndex() {
        return RepositoryFactory.getTraceIndexRepository().find();
    }

    /**
     * Get Trace list by query condition.
     *
     * @param query
     * @return
     */
    public List<TraceOverviewTable> findTraceList(Map<String, String> query) {
        return RepositoryFactory.getTraceOverviewRepository().find(query);
    }

    /**
     * Get TraceTable list by scan condition.
     *
     * @param scan
     * @return
     */
    public List<TraceOverviewTable> findTraceList(Scan scan) {
        return RepositoryFactory.getTraceOverviewRepository().find(scan);
    }

    /**
     * Get Span list by traceId.
     *
     * @param traceId
     * @return
     */
    public List<Span> findTrace(String traceId) {
        return RepositoryFactory.getTraceRepository().find(traceId);
    }

    /**
     * Get span name by traceId.
     *
     * @param traceId
     * @return
     */
    public Map<String, Integer> findSpanName(String traceId) {
        return RepositoryFactory.getTraceRepository().findSpanName(traceId);
    }

    /**
     * Get exception index.
     *
     * @return
     */
    public List<Map<String, Object>> findExceptionIndex() {
        return RepositoryFactory.getExceptionIndexRepository().find();
    }

    /**
     * Get Exception list
     *
     * @param query
     * @return
     */
    public List<Map<String, Object>> findExceptionList(Map<String, String> query) {
        return RepositoryFactory.getExceptionRepository().find(query);
    }


    public Map<String, Object> findTopList() {
        return RepositoryFactory.getTopRepository().find();
    }

    public void saveMetricIndex(MetricData metric) {
        RepositoryFactory.getTsdbIndexReporsitory().save(metric);
    }

    public void saveMetric(MetricData metric) {
        RepositoryFactory.getTsdbRepository().save(metric);
    }

    public List<Map<String, Object>> findMetricsIndex() {
        return RepositoryFactory.getTsdbIndexReporsitory().find();
    }

    public Map<String, Object> findName1(final String app, final String name1) {
        return RepositoryFactory.getTsdbIndexReporsitory().findName1(app, name1);
    }

    public Map<String, Object> findName2(final String app, final String name1) {
        return RepositoryFactory.getTsdbIndexReporsitory().findName2(app, name1);
    }

    public List<Map<String, Object>> findMetrics(Map<String, String> query) {
        return RepositoryFactory.getTsdbRepository().find(query);
    }

}
