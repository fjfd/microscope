package com.vipshop.microscope.storage.hbase;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Scan;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.storage.hbase.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.factory.HbaseFactory;

public class HbaseQueryEngine {
	
	public static List<Map<String, Object>> findAppIPTrace() {
		return HbaseFactory.getAppTableRepository().findAppIPTrace();
	}
	
	public static List<TraceTable> find(Map<String, String> query) {
		return HbaseFactory.getTraceTableRepository().find(query);
	}
	
	public static List<TraceTable> find(Scan scan) {
		return HbaseFactory.getTraceTableRepository().find(scan);
	}
	
	public static List<Span> find(String traceId) {
		return HbaseFactory.getSpanTableRepository().find(traceId);
	}

	public static Map<String, Integer> findSpanName(String traceId) {
		return HbaseFactory.getSpanTableRepository().findSpanName(traceId);
	}

}
