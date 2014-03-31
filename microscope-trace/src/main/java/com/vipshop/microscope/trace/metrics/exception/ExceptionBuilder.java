package com.vipshop.microscope.trace.metrics.exception;

import java.util.HashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.stoarge.Storage;
import com.vipshop.microscope.trace.stoarge.StorageHolder;

/**
 * Record exception API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionBuilder {
	
	private static final Storage storage = StorageHolder.getStorage();
	
	public static void record(final Throwable t) {
		HashMap<String, Object> exception = new HashMap<String, Object>();
		
		exception.put("type", MetricsCategory.Exception);
		exception.put("IP", IPAddressUtil.IPAddress());
		exception.put("APP", Tracer.APP_NAME);
		exception.put("Date", TimeStampUtil.currentTimeMillis());
		exception.put("Name", t.getClass().getName());
		exception.put("Message", ExceptionUtils.getMessage(t));
		exception.put("Stack", ExceptionUtils.getStackTrace(t));
		exception.put("TraceId", Tracer.getTraceId());
		
		storage.addMetrics(exception);
	}
	
	public static void record(final Throwable t, String info) {
		HashMap<String, Object> excetpion = new HashMap<String, Object>();
		
		excetpion.put("type", MetricsCategory.Exception);
		excetpion.put("IP", IPAddressUtil.IPAddress());
		excetpion.put("APP", Tracer.APP_NAME);
		excetpion.put("Date", TimeStampUtil.currentTimeMillis());
		excetpion.put("Name", t.getClass().getName());
		excetpion.put("Message", ExceptionUtils.getMessage(t));
		excetpion.put("Stack", ExceptionUtils.getStackTrace(t));
		excetpion.put("TraceId", Tracer.getTraceId());
		excetpion.put("Debug", info);
		
		storage.addMetrics(excetpion);
	}
	
}
