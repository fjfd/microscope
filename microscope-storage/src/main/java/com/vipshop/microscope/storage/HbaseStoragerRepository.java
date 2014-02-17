package com.vipshop.microscope.storage;

import com.vipshop.microscope.framework.thrift.Span;
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
		AppTable appTable = AppTable.build(span);
		TraceTable traceTable = TraceTable.build(span);
		this.save(appTable);
		this.save(traceTable);
		this.save(span);
	}
	
	private void save(AppTable appTable) {
		if (appTable != null) {
			HbaseRepository.save(appTable);
		}
	}
	
	private void save(TraceTable traceTable) {
		if (traceTable != null) {
			HbaseRepository.save(traceTable);
		}
	}

	private void save(Span span) {
		if (span != null) {
			HbaseRepository.save(span);
		}
	}
}
