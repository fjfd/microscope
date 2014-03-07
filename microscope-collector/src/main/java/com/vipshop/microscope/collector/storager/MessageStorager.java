package com.vipshop.microscope.collector.storager;

import java.util.Map;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.hbase.domain.AppTable;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;

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
	private StorageRepository storageRepository = StorageRepository.getStorageRepository();
	
	public void storage(Span span) {
		if (span == null) {
			return;
		}
		
		String traceId = String.valueOf(span.getTraceId());
		String spanId = String.valueOf(span.getSpanId());
		
		if (traceId.equals(spanId)) {
			
			String appName = span.getAppName();
			String traceName = span.getSpanName();

			// remove user id from span name
			if (appName.equals("user_info") && traceName.matches(".*\\d.*")) {
				traceName = traceName.replaceAll("\\d", "");
				traceName = traceName.substring(7);
				span.setSpanName(traceName);
			}
			storageRepository.save(AppTable.build(span));
			storageRepository.save(TraceTable.build(span));
		}
		
		storageRepository.save(span);
	}
	
	public void storage(Map<String, Object> map) {
		storageRepository.save(map);
	}
	
}
