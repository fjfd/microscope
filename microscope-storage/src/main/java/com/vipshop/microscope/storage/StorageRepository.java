package com.vipshop.microscope.storage;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.hbase.HbaseStoreEngine;
import com.vipshop.microscope.storage.mysql.MySQLStoreEngine;

public class StorageRepository {
	
	private HbaseStoreEngine hbaseStoreEngine = new HbaseStoreEngine();
	
	private MySQLStoreEngine mysqlStoreEngine = new MySQLStoreEngine();
	
	public void storage(Span span) {
		hbaseStoreEngine.storage(span);
	}
	
	public void analyze(Span span) {
		mysqlStoreEngine.analyze(span);
	}
	
}
