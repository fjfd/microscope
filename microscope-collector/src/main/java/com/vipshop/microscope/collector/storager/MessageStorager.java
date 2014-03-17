package com.vipshop.microscope.collector.storager;

import java.util.Map;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.hbase.domain.AppTable;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;

/**
 * Message Store API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageStorager {
	
	/**
	 * the main storage executor.
	 */
	private StorageRepository storageRepository = StorageRepository.getStorageRepository();

	private static class MessageStoragerHolder {
		private static final MessageStorager messageStorager = new MessageStorager();
	}
	
	public static MessageStorager getMessageStorager() {
		return MessageStoragerHolder.messageStorager;
	}
	
	private MessageStorager() {}
	
	/**
	 * Store span message.
	 * 
	 * @param span
	 */
	public void storage(Span span) {
		String traceId = String.valueOf(span.getTraceId());
		String spanId = String.valueOf(span.getSpanId());
		if (traceId.equals(spanId)) {
			storageRepository.save(AppTable.build(span));
			storageRepository.save(TraceTable.build(span));
		}
		storageRepository.save(span);
	}
	
	/**
	 * Store exception message.
	 * 
	 * @param map
	 */
	public void storage(Map<String, Object> map) {
		storageRepository.save(map);
	}
	
	public void storage() {
		
	}
	
}
