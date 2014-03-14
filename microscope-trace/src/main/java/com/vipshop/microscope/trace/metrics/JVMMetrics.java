package com.vipshop.microscope.trace.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

/**
 * JVM metrics
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class JVMMetrics {
	
	private static final MetricRegistry metrics = MetricsContainer.getMetricRegistry();
	
	public static void registerJVM() {
		metrics.register("Thread", new ThreadStatesGaugeSet());
		metrics.register("Memory", new MemoryUsageGaugeSet());
		metrics.register("GC", new GarbageCollectorMetricSet());
	}
	
}
