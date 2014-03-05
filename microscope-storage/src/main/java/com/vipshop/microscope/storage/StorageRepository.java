package com.vipshop.microscope.storage;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.hbase.HbaseStoreEngine;

public class StorageRepository {
	
	private HbaseStoreEngine hbaseStoreEngine = new HbaseStoreEngine();
	
	public void storage(Span span) {
		hbaseStoreEngine.storage(span);
	}
	
}
