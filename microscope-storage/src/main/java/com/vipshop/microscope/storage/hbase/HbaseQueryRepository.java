package com.vipshop.microscope.storage.hbase;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Scan;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.factory.RepositoryFactory;

/**
 * Hbase Query API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class HbaseQueryRepository {
	
	/**
	 * Get App name, IP address, Trace name
	 * from AppTable.
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAppIPTrace() {
		return RepositoryFactory.getAppTableRepository().findAppIPTrace();
	}
	
	/**
	 * Get TraceTable list by query condition.
	 * 
	 * @param query
	 * @return
	 */
	public List<TraceTable> find(Map<String, String> query) {
		return RepositoryFactory.getTraceTableRepository().find(query);
	}
	
	/**
	 * Get TraceTable list by scan condition.
	 * 
	 * @param scan
	 * @return
	 */
	public List<TraceTable> find(Scan scan) {
		return RepositoryFactory.getTraceTableRepository().find(scan);
	}
	
	/**
	 * Get Span list by traceId.
	 * 
	 * @param traceId
	 * @return
	 */
	public List<Span> find(String traceId) {
		return RepositoryFactory.getSpanTableRepository().find(traceId);
	}

	/**
	 * Get span name by traceId.
	 * 
	 * @param traceId
	 * @return
	 */
	public Map<String, Integer> findSpanName(String traceId) {
		return RepositoryFactory.getSpanTableRepository().findSpanName(traceId);
	}
	
	/**
	 * Get App name, IP address, Exception name
	 * 
	 * from exception_index table.
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAppIPName() {
		return RepositoryFactory.getExceptionTableRepository().findAppIPName();
	}

	/**
	 * Get Exception list by query.
	 * from exception table.
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> findExcepList(Map<String, String> query) {
		return RepositoryFactory.getExceptionTableRepository().find(query);
	}
	
	public List<Map<String, Object>> findAppIP() {
		return RepositoryFactory.getJVMTableRepository().findAppIP();
	}
	
	public List<Map<String, Object>> findJVMList(Map<String, String> query) {
		return RepositoryFactory.getJVMTableRepository().find(query);
	}

}
