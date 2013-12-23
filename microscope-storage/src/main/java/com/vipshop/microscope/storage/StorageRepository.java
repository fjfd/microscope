package com.vipshop.microscope.storage;

import com.vipshop.micorscope.framework.thrift.Span;

public class StorageRepository {
	
	private HbaseStoragerRepository hbaseStorager = new HbaseStoragerRepository();
	
	public void storage(Span span) {
		hbaseStorager.storage(span);
	}
	
}
