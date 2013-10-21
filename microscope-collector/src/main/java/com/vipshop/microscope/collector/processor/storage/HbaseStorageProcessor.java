package com.vipshop.microscope.collector.processor.storage;

import com.vipshop.microscope.hbase.domain.App;
import com.vipshop.microscope.hbase.domain.TraceIndex;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.thrift.Span;

public class HbaseStorageProcessor implements StorageProcessor {

	@Override
	public void save(App appIndex) {
		if (appIndex != null && !this.exist(appIndex.getAppName())) {
			Repositorys.APP_INDEX.save(appIndex);
		}
	}
	
	@Override
	public void save(TraceIndex traceIndex) {
		if (traceIndex != null && !this.exist(traceIndex)) {
			Repositorys.TRAC_INDEX.save(traceIndex);
		}
	} 

	@Override
	public void save(TraceTable traceTable) {
		if (traceTable != null) {
			Repositorys.TRAC.save(traceTable);
		}
	}

	@Override
	public void save(Span span) {
		if (span != null) {
			Repositorys.TRACE_SPAN.save(String.valueOf(span.getTrace_id()), span.getName());
			Repositorys.SPAN.save(span);
		}
	}

	@Override
	public boolean exist(String appName) {
		return Repositorys.APP_INDEX.exist(appName);
	}

	@Override
	public boolean exist(TraceIndex traceIndex) {
		return Repositorys.TRAC_INDEX.exist(traceIndex);
	}

}
