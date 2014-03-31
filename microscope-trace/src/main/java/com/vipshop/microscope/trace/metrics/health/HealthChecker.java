package com.vipshop.microscope.trace.metrics.health;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.codahale.metrics.health.HealthCheck;
import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.metrics.MetricsStats;
import com.vipshop.microscope.trace.stoarge.Storage;
import com.vipshop.microscope.trace.stoarge.StorageHolder;

public class HealthChecker {
	
	private static final Storage storage = StorageHolder.getStorage();
	
	public static void check() {
		Map<String, HealthCheck.Result> results = MetricsStats.runHealthChecks();
        if (!results.isEmpty()) {
        	HashMap<String, Object> metrics = new HashMap<String, Object>();
        	metrics.put("type", MetricsCategory.Health);
    		metrics.put("date", System.currentTimeMillis());
    		metrics.put("app", Tracer.APP_NAME);
    		metrics.put("ip", IPAddressUtil.IPAddress());
    		for (Entry<String, HealthCheck.Result> entry : results.entrySet()){
    			HashMap<String, Object> values = new HashMap<String, Object>();
    			values.put("isHealthy", entry.getValue().isHealthy());
    			values.put("message", entry.getValue().getMessage());
    			metrics.put(entry.getKey(), values);
    		}
    		storage.addMetrics(metrics);
		}
	}
	
//	for (Entry<String, HealthCheck.Result> entry : results.entrySet()) {
//	    if (entry.getValue().isHealthy()) {
//	        System.out.println(entry.getKey() + " is healthy");
//	    } else {
//	        System.err.println(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
//	        final Throwable e = entry.getValue().getError();
//	        if (e != null) {
//	            e.printStackTrace();
//	        }
//	    }
//	}     
}
