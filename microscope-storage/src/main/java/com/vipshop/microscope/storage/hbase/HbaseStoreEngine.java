package com.vipshop.microscope.storage.hbase;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.hbase.domain.AppTable;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.factory.HbaseFactory;

public class HbaseStoreEngine {
	
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
	
	public void save(AppTable appTable) {
		HbaseFactory.getAppTableRepository().save(appTable);
	}
	
	public void save(TraceTable traceTable) {
		HbaseFactory.getTraceTableRepository().save(traceTable);
	}
	
	public void save(Span span) {
		HbaseFactory.getSpanTableRepository().save(span);
	}
}
