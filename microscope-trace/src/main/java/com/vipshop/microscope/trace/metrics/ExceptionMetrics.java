package com.vipshop.microscope.trace.metrics;

import java.util.HashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.stoarge.Storage;
import com.vipshop.microscope.trace.stoarge.StorageHolder;

/**
 * A ExceptionBuilder use for build exception info.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionMetrics {
	
	private static final Storage storage = StorageHolder.getStorage();
	
	public static void record(final Throwable t) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("IP", IPAddressUtil.IPAddress());
		map.put("APP", Tracer.APP_NAME);
		map.put("Date", TimeStampUtil.currentTimeMillis());
		map.put("Name", t.getClass().getName());
		map.put("Message", ExceptionUtils.getMessage(t));
		map.put("Stack", ExceptionUtils.getStackTrace(t));
		map.put("TraceId", Tracer.getTraceId());
		
		storage.addException(map);
	}
	
	public static void record(final Throwable t, String info) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("IP", IPAddressUtil.IPAddress());
		map.put("APP", Tracer.APP_NAME);
		map.put("Date", TimeStampUtil.currentTimeMillis());
		map.put("Name", t.getClass().getName());
		map.put("Message", ExceptionUtils.getMessage(t));
		map.put("Stack", ExceptionUtils.getStackTrace(t));
		map.put("TraceId", Tracer.getTraceId());
		map.put("Debug", info);
		
		storage.addException(map);
	}
	
}
