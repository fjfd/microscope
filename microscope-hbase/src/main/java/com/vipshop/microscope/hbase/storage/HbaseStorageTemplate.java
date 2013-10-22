package com.vipshop.microscope.hbase.storage;

import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.thrift.Span;

public class HbaseStorageTemplate {

	public void save(AppTrace appIndex) {
		if (appIndex != null) {
			Repositorys.APP_TRACE.save(appIndex);
		}
	}
	
	public void save(TraceTable traceTable) {
		if (traceTable != null) {
			Repositorys.TRACE.save(traceTable);
		}
	}

	public void save(Span span) {
		if (span != null) {
			Repositorys.SPAN.save(span);
		}
	}

}
