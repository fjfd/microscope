package com.vipshop.microscope.trace.metrics;

import java.util.HashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.Tracer;

/**
 * A ExceptionBuilder use for build exception info.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionMetrics {
	
	public static HashMap<String, Object> record(final Throwable t) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("IP", IPAddressUtil.IPAddress());
		map.put("APP", Tracer.APP_NAME);
		map.put("Date", TimeStampUtil.currentTimeMillis());
		map.put("Name", t.getClass().getName());
		map.put("Message", ExceptionUtils.getMessage(t));
		map.put("Stack", ExceptionUtils.getStackTrace(t));
		map.put("TraceId", Tracer.getTraceId());
		
		return map;
	}
	
	public static HashMap<String, Object> record(final Throwable t, String info) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("IP", IPAddressUtil.IPAddress());
		map.put("APP", Tracer.APP_NAME);
		map.put("Date", TimeStampUtil.currentTimeMillis());
		map.put("Name", t.getClass().getName());
		map.put("Message", ExceptionUtils.getMessage(t));
		map.put("Stack", ExceptionUtils.getStackTrace(t));
		map.put("TraceId", Tracer.getTraceId());
		map.put("Debug", info);
		
		return map;
	}
	
}
