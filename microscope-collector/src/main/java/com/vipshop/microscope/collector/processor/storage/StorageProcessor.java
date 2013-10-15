package com.vipshop.microscope.collector.processor.storage;

import com.vipshop.microscope.hbase.domain.App;
import com.vipshop.microscope.hbase.domain.TraceIndex;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.thrift.Span;

public interface StorageProcessor {
	
	public void save(App appIndex);
	
	public void save(TraceIndex traceIndex);
	
	public void save(TraceTable traceTable);
	
	public void save(Span span);
	
	public boolean exist(String appName);
	
	public boolean exist(TraceIndex traceIndex);
	
	
}
