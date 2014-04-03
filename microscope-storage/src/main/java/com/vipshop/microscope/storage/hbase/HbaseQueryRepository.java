package com.vipshop.microscope.storage.hbase;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Scan;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.hbase.factory.RepositoryFactory;
import com.vipshop.microscope.storage.hbase.table.TraceOverviewTable;

/**
 * Hbase Query API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class HbaseQueryRepository {
	
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
	
	/**
	 * Get report index.
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findReportIndex() {
		return RepositoryFactory.getReportIndexRepository().find();
	}

	public Map<String, Object> findTopList() {
		return RepositoryFactory.getTopRepository().find();
	}

	public List<Map<String, Object>> findJVMListInitLoad(Map<String, String> query) {
		return RepositoryFactory.getJVMRepository().findInitLoad(query);
	}

	public List<Map<String, Object>> findJVMList(Map<String, String> query) {
		return RepositoryFactory.getJVMRepository().find(query);
	}
	
	public List<Map<String, Object>> findJVMListByTime(Map<String, String> query) {
		return RepositoryFactory.getJVMRepository().findByTime(query);
	}
	
	public List<Map<String, Object>> findServletList(Map<String, String> query) {
		return RepositoryFactory.getServletRepository().find(query);
	}
	
	public List<Map<String, Object>> findUserHistory() {
		return RepositoryFactory.getUserRepository().findUserHistory();
	}

}
