package com.vipshop.microscope.query.core;

import java.util.List;
import java.util.Map;

import com.vipshop.microscope.hbase.domain.App;
import com.vipshop.microscope.hbase.domain.TraceIndex;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.TraceFactory;

public class QueryTemplate {
	
	/**
	 * Returns all app name.
	 * 
	 * @return
	 */
	public List<App> getAppIndex() {
		return Repositorys.APP_INDEX.findAll();
	}
	
	/**
	 * Returns all {@code TraceIndex} name by app name.
	 * 
	 * @param appName
	 * @return
	 */
	public List<TraceIndex> getTraceIndexByAppName(String appName) {
		return Repositorys.TRAC_INDEX.findByAppName(appName);
	}
	
	/**
	 * Returns all trace name by app name.
	 * 
	 * @param appName
	 * @return
	 */
	public List<String> getTraceNameByAppName(String appName) {
		TraceFactory.getTrace().clientSend("getTraceNameByAppName");
		List<String> strings = Repositorys.TRAC_INDEX.findTraceNameByAppName(appName);
		TraceFactory.getTrace().clientReceive();
		return strings;
	}

	/**
	 * Returns trace list by trace name.
	 * 
	 * @param name
	 * @return
	 */
	public List<TraceTable> getTraceListByTraceQuery(Map<String, String> query) {
		return Repositorys.TRAC.findByName(query);
	}
	
	public TraceTable getTraceTableByTraceId(String traceId) {
		return Repositorys.TRAC.findByTraceId(traceId);
	}
	/**
	 * Returns span names by trace id.
	 * 
	 * @param traceId
	 * @return
	 */
	public Map<String, String> getSpanNameByTraceId(String traceId) {
		return Repositorys.TRACE_SPAN.find(traceId);
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
