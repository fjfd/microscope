package com.vipshop.microscope.trace.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vipshop.microscope.common.logentry.Codec;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.Tracer;

public class ExceptionBuilder {
	
	public static LogEntry record(final Throwable t) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("IP", IPAddressUtil.IPAddress());
		map.put("APP", Tracer.APP_NAME);
		map.put("Date", TimeStampUtil.currentTimeMillis());
		map.put("Name", t.getClass().getName());
		map.put("Message", ExceptionUtils.getMessage(t));
		map.put("Stack", ExceptionUtils.getStackTrace(t));
		map.put("TraceId", Tracer.getTraceId());
		
		return Codec.encodeToLogEntry(map);
	}
	
	public static LogEntry record(final Throwable t, String info) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("IP", IPAddressUtil.IPAddress());
		map.put("APP", Tracer.APP_NAME);
		map.put("Date", TimeStampUtil.currentTimeMillis());
		map.put("Name", t.getClass().getName());
		map.put("Message", ExceptionUtils.getMessage(t));
		map.put("Stack", ExceptionUtils.getStackTrace(t));
		map.put("TraceId", Tracer.getTraceId());
		map.put("Debug", info);
		
		return Codec.encodeToLogEntry(map);
	}
	
}
