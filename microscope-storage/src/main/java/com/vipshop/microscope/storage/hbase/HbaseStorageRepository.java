package com.vipshop.microscope.storage.hbase;

import java.util.Map;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.hbase.domain.AppTable;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.factory.RepositoryFactory;

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
		RepositoryFactory.getAppTableRepository().initialize();
		RepositoryFactory.getTraceTableRepository().initialize();
		RepositoryFactory.getSpanTableRepository().initialize();
	}
	
	/**
	 * Drop hbase tables.
	 */
	public void drop() {
		RepositoryFactory.getAppTableRepository().drop();
		RepositoryFactory.getTraceTableRepository().drop();
		RepositoryFactory.getSpanTableRepository().drop();
	}
	
	/**
	 * Drop hbast tables then create tables.
	 */
	public void reInitalize() {
		this.drop();
		this.create();
	}
	
	/**
	 * Store {@link AppTable} to hbase.
	 * 
	 * @param appTable
	 */
	public void save(AppTable appTable) {
		RepositoryFactory.getAppTableRepository().save(appTable);
	}
	
	/**
	 * Store {@link TraceTable} to hbase.
	 * 
	 * @param traceTable
	 */
	public void save(TraceTable traceTable) {
		RepositoryFactory.getTraceTableRepository().save(traceTable);
	}
	
	/**
	 * Store {@link Span} to hbase.
	 * 
	 * @param span
	 */
	public void save(Span span) {
		RepositoryFactory.getSpanTableRepository().save(span);
	}
	
	/**
	 * Store Map ojbect to hbase, exception table.
	 * 
	 * @param map
	 */
	public void save(Map<String, Object> map) {
		RepositoryFactory.getExceptionTableRepository().save(map);
	}
	
}
