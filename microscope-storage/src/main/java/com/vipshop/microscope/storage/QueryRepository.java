package com.vipshop.microscope.storage;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Scan;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.hbase.HbaseQueryRepository;
import com.vipshop.microscope.storage.hbase.table.TraceOverviewTable;

/**
 * Query API.
 * 
 * Query API responsible for acquire data from database.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class QueryRepository {

	private static class QueryRepositoryHolder {
		public static QueryRepository queryRepository = new QueryRepository();
	}
	
	public static QueryRepository getQueryRepository() {
		return QueryRepositoryHolder.queryRepository;
	}
	
	private final HbaseQueryRepository hbaseQueryRepository = new HbaseQueryRepository();
	
	public List<Map<String, Object>> findTraceIndex() {
		return hbaseQueryRepository.findTraceIndex();
	}
	
	public List<TraceOverviewTable> findTraceList(Map<String, String> query) {
		return hbaseQueryRepository.findTraceList(query);
	}
	
	public List<TraceOverviewTable> findTraceList(Scan scan) {
		return hbaseQueryRepository.findTraceList(scan);
	}
	
	public List<Span> findTrace(String traceId) {
		return hbaseQueryRepository.findTrace(traceId);
	}
	
	public Map<String, Integer> findSpanName(String traceId) {
		return hbaseQueryRepository.findSpanName(traceId);
	}
	
	public List<Map<String, Object>> findExceptionIndex() {
		return hbaseQueryRepository.findExceptionIndex();
	}
	
	public List<Map<String, Object>> findExceptionList(Map<String, String> query) {
		return hbaseQueryRepository.findExceptionList(query);
	}
	
	public List<Map<String, Object>> findReportIndex() {
		return hbaseQueryRepository.findReportIndex();
	}
	
	public Map<String, Object> findTopList() {
		return hbaseQueryRepository.findTopList();
	}
	
	public List<Map<String, Object>> findServletList(Map<String, String> query) {
		return hbaseQueryRepository.findServletList(query);
	}
	
	public List<Map<String, Object>> findJVMList(Map<String, String> query) {
		return hbaseQueryRepository.findJVMList(query);
	}
	
	public List<Map<String, Object>> findJVMListInitLoad(Map<String, String> query) {
		return hbaseQueryRepository.findJVMListInitLoad(query);
	}
	
	public List<Map<String, Object>> findJVMListByTime(Map<String, String> query) {
		return hbaseQueryRepository.findJVMListByTime(query);
	}
	
	public List<Map<String, Object>> findUserHistory() {
		return hbaseQueryRepository.findUserHistory();
	}
	
 }
