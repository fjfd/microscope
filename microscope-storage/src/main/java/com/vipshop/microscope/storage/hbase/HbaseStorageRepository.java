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
		RepositoryFactory.getExceptionIndexRepository().initialize();
		RepositoryFactory.getExceptionRepository().initialize();
		RepositoryFactory.getJVMRepository().initialize();
		RepositoryFactory.getTopRepository().initialize();
		RepositoryFactory.getUserRepository().initialize();
		RepositoryFactory.getServletRepository().initialize();
	}
	
	/**
	 * Drop hbase tables.
	 */
	public void drop() {
		RepositoryFactory.getTraceIndexRepository().drop();
		RepositoryFactory.getTraceOverviewRepository().drop();
		RepositoryFactory.getTraceRepository().drop();
		RepositoryFactory.getExceptionIndexRepository().drop();
		RepositoryFactory.getExceptionRepository().drop();
		RepositoryFactory.getJVMRepository().drop();
		RepositoryFactory.getTopRepository().drop();
		RepositoryFactory.getUserRepository().drop();
		RepositoryFactory.getServletRepository().drop();
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
	
	public void saveReportIndex(Map<String, Object> report) {
		RepositoryFactory.getReportIndexRepository().save(report);
	}
	
	/**
	 * Store jvm metrics to jvm table.
	 * 
	 * @param jvm
	 */
	public void saveJVM(Map<String, Object> jvm) {
		RepositoryFactory.getJVMRepository().save(jvm);
	}
	
	public void saveServletActiveRequest(Map<String, Object> activeRequest) {
		RepositoryFactory.getServletRepository().saveActiveRequest(activeRequest);
	}
	
	public void saveServletResponseCode(Map<String, Object> responseCode) {
		RepositoryFactory.getServletRepository().saveResponseCode(responseCode);
	}
	
	public void saveServletRequest(Map<String, Object> request) {
		RepositoryFactory.getServletRepository().saveRequest(request);
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
	
}
