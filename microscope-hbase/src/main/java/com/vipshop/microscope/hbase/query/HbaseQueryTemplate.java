package com.vipshop.microscope.hbase.query;

import java.util.List;
import java.util.Map;

import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.thrift.Span;

public class HbaseQueryTemplate {
	
	/**
	 * Returns app-with-trace info.
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAppTrace() {
		return Repositorys.APP_TRACE.findAll();
	}
	
	/**
	 * Returns trace list by trace name.
	 * 
	 * @param name
	 * @return
	 */
	public List<TraceTable> getTraceListByTraceQuery(Map<String, String> query) {
		return Repositorys.TRACE.findByName(query);
	}
	
	public Map<String, String> findSpanNameByTraceId(String traceId) {
		return Repositorys.SPAN.findSpanNameByTraceId(traceId);
	}
	
	public TraceTable getTraceTableByTraceId(String traceId) {
		return Repositorys.TRACE.findByTraceId(traceId);
	}
	
	/**
	 * Returns spans by trace id.
	 * 
	 * @param traceId
	 * @return
	 */
	public List<Span> getSpanByTraceId(String traceId) {
		return Repositorys.SPAN.findSpanByTraceId(traceId);
	}
	
}
