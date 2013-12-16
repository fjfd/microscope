package com.vipshop.microscope.collector.storager;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * Store spans.
 * 
 * @see com.vipshop.microscope.storage.StorageRepository
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageStorager {
	
	/**
	 * main storage executor.
	 */
	private StorageRepository repository = new StorageRepository();
	
	public void storage(Span span) {
		repository.storage(span);
	}
	
}
