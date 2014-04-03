package com.vipshop.microscope.storage;

import java.util.Map;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.hbase.HbaseStorageRepository;
import com.vipshop.microscope.storage.hbase.table.TraceIndexTable;
import com.vipshop.microscope.storage.hbase.table.TraceOverviewTable;
import com.vipshop.microscope.storage.mysql.MySQLStorageRepository;

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
	
	private final HbaseStorageRepository hbaseStorageRepository = new HbaseStorageRepository();
	
	private final MySQLStorageRepository mysqlStorageRepository = new MySQLStorageRepository();
	
	public void createHbaseTable() {
		hbaseStorageRepository.create();
	}
	
	public void dropHbaseTable() {
		hbaseStorageRepository.drop();
	}
	
	public void reInitalizeHbaseTable() {
		hbaseStorageRepository.reInitalize();
	}
	
	public void save(TraceIndexTable appTable) {
		hbaseStorageRepository.save(appTable);
	}
	
	public void save(TraceOverviewTable traceTable) {
		hbaseStorageRepository.save(traceTable);
	}
	
	public void save(Span span) {
		hbaseStorageRepository.save(span);
	}
	
	public void saveException(Map<String, Object> exception) {
		hbaseStorageRepository.saveExceptionIndex(exception);
		hbaseStorageRepository.saveException(exception);
	}
	
	public void saveJVM(Map<String, Object> jvm) {
		hbaseStorageRepository.saveJVM(jvm);
	}
	
	public void saveServletActiveRequest(Map<String, Object> counter) {
		hbaseStorageRepository.saveServletActiveRequest(counter);
	}
	
	public void saveServletResponseCode(Map<String, Object> meter) {
		hbaseStorageRepository.saveServletResponseCode(meter);
	}
	
	public void saveServletRequest(Map<String, Object> timer) {
		hbaseStorageRepository.saveServletRequest(timer);
	}
	
	public void saveTop(Map<String, Object> top) {
		hbaseStorageRepository.saveTop(top);
	}
	
	public void saveUser(Map<String, String> user) {
		hbaseStorageRepository.saveUser(user);
	}
	
	public void createMySQLTable() {
		mysqlStorageRepository.create();
	}
	
}
