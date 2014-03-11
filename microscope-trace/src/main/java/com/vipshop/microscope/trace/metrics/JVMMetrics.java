package com.vipshop.microscope.trace.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

public class JVMMetrics {
	
	private static final MetricRegistry metrics = MetricsContainer.getMetricRegistry();
	
	public static void registerJVM() {
		metrics.register("Thread", new ThreadStatesGaugeSet());
		metrics.register("Memory", new MemoryUsageGaugeSet());
		metrics.register("GC", new GarbageCollectorMetricSet());
	}
	
	public static void registerJVM(long period, TimeUnit unit) {
		metrics.register("Thread", new ThreadStatesGaugeSet());
		metrics.register("Memory", new MemoryUsageGaugeSet());
		metrics.register("GC", new GarbageCollectorMetricSet());
	}

}
