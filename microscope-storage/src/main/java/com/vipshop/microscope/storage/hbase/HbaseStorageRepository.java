package com.vipshop.microscope.storage.hbase;

import java.util.Map;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.hbase.factory.RepositoryFactory;
import com.vipshop.microscope.storage.hbase.table.TraceIndexTable;
import com.vipshop.microscope.storage.hbase.table.TraceOverviewTable;

/**
 * Hbase Storage API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class HbaseStorageRepository {
	
	/**
	 * Create hbase tables.
	 */
	public void create() {
		RepositoryFactory.getTraceIndexRepository().initialize();
		RepositoryFactory.getTraceOverviewRepository().initialize();
		RepositoryFactory.getTraceRepository().initialize();
		RepositoryFactory.getExceptionRepository().initialize();
		RepositoryFactory.getJVMTableRepository().initialize();
		RepositoryFactory.getTopTableRepository().initialize();
		RepositoryFactory.getUserTableRepository().initialize();
		RepositoryFactory.getServletTableRepository().initialize();
	}
	
	/**
	 * Drop hbase tables.
	 */
	public void drop() {
		RepositoryFactory.getTraceIndexRepository().drop();
		RepositoryFactory.getTraceOverviewRepository().drop();
		RepositoryFactory.getTraceRepository().drop();
		RepositoryFactory.getExceptionRepository().drop();
		RepositoryFactory.getJVMTableRepository().drop();
		RepositoryFactory.getTopTableRepository().drop();
		RepositoryFactory.getUserTableRepository().drop();
		RepositoryFactory.getServletTableRepository().drop();
	}
	
	/**
	 * Drop hbast tables then create tables.
	 */
	public void reInitalize() {
		this.drop();
		this.create();
	}
	
	/**
	 * Store {@link TraceIndexTable} to hbase.
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
	 * @param map
	 */
	public void saveExceptionIndex(Map<String, Object> exception) {
		RepositoryFactory.getExceptionIndexRepository().save(exception);
	}

	
	/**
	 * Store Map ojbect to exception table.
	 * 
	 * @param map
	 */
	public void saveException(Map<String, Object> excption) {
		RepositoryFactory.getExceptionRepository().save(excption);
	}
	
	/**
	 * Store jvm metrics to jvm table.
	 * 
	 * @param jvm
	 */
	public void saveJVM(Map<String, Object> jvm) {
		RepositoryFactory.getJVMTableRepository().save(jvm);
	}
	
	public void saveServletActiveRequest(Map<String, Object> activeRequest) {
		RepositoryFactory.getServletTableRepository().saveActiveRequest(activeRequest);
	}
	
	public void saveServletResponseCode(Map<String, Object> responseCode) {
		RepositoryFactory.getServletTableRepository().saveResponseCode(responseCode);
	}
	
	public void saveServletRequest(Map<String, Object> request) {
		RepositoryFactory.getServletTableRepository().saveRequest(request);
	}
	
	/**
	 * Store top report to top table.
	 * 
	 * @param top
	 */
	public void saveTop(Map<String, Object> top) {
		RepositoryFactory.getTopTableRepository().save(top);
	}
	
	/**
	 * Store user query info to hbase.
	 * 
	 * @param user
	 */
	public void saveUser(Map<String, String> user) {
		RepositoryFactory.getUserTableRepository().save(user);
	}
	
}
