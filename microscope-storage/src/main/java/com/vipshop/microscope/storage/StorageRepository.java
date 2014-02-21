package com.vipshop.microscope.storage;

import com.vipshop.microscope.common.thrift.Span;

public class StorageRepository {
	
	private HbaseStoragerRepository hbaseStorager = new HbaseStoragerRepository();
	
	public void storage(Span span) {
		hbaseStorager.storage(span);
	}
	
}
