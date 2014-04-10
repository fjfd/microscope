package com.vipshop.microscope.storage;

import java.util.List;
import java.util.Map;

import com.vipshop.microscope.common.metrics.Metric;
import org.apache.hadoop.hbase.client.Scan;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.hbase.HbaseRepository;
import com.vipshop.microscope.storage.hbase.table.TraceIndexTable;
import com.vipshop.microscope.storage.hbase.table.TraceOverviewTable;
import com.vipshop.microscope.storage.mysql.MySQLStorageRepository;
import com.vipshop.microscope.storage.opentsdb.OpenTSDBRepository;
import com.vipshop.microscope.storage.opentsdb.core.Aggregator;
import com.vipshop.microscope.storage.opentsdb.core.DataPoints;

/**
 * Storage API.
 * 
 * Storage API responsible for save data to database.
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
	
	private final HbaseRepository hbaseRepository = new HbaseRepository();
	
	private final OpenTSDBRepository openTSDBRepository = new OpenTSDBRepository();
	
	private final MySQLStorageRepository mysqlStorageRepository = new MySQLStorageRepository();
	
	public void createHbaseTable() {
		hbaseRepository.create();
	}
	
	public void dropHbaseTable() {
		hbaseRepository.drop();
	}
	
	public void reInitalizeHbaseTable() {
		hbaseRepository.reInitalize();
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
			 Aggregator function, boolean rate ){
		return openTSDBRepository.find(starttimestamp, System.currentTimeMillis(), metric, tags, function, rate);
	}
	
	public DataPoints[] find(long starttimestamp, long endtimestamp, 
			 String metric, Map<String, String> tags,
			 Aggregator function, boolean rate ) {
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
	
	public void createMySQLTable() {
		mysqlStorageRepository.create();
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

    public void saveMetricIndex(Metric metric) {
        hbaseRepository.saveMetricIndex(metric);
    }

    public void saveMetric(Metric metric) {
        hbaseRepository.saveMetric(metric);
    }


	
}
