package com.vipshop.microscope.storage;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.domain.AppTable;
import com.vipshop.microscope.storage.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.HbaseRepository;

/**
 * Store span to hbase.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class HbaseStoragerRepository {
	
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
			this.save(AppTable.build(span));
			this.save(TraceTable.build(span));
		}
		
		this.save(span);
	}
	
	private void save(AppTable appTable) {
		HbaseRepository.save(appTable);
	}
	
	private void save(TraceTable traceTable) {
		HbaseRepository.save(traceTable);
	}

	private void save(Span span) {
		HbaseRepository.save(span);
	}
}
