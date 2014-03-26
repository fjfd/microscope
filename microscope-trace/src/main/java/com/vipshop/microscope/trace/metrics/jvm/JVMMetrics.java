package com.vipshop.microscope.trace.metrics.jvm;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.trace.metrics.MetricsHolder;

/**
 * JVM metrics
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class JVMMetrics {

	private static final MetricRegistry metrics = MetricsHolder.getMetricRegistry();

	public static void registerJVM() {
		metrics.register(MetricsCategory.JVM_Thread, new ThreadMetricsSet());
		metrics.register(MetricsCategory.JVM_Memory, new MemoryUsageGaugeSet());
		metrics.register(MetricsCategory.JVM_GC, new GarbageCollectorMetricSet());
		metrics.register(MetricsCategory.JVM_Runtime, new RuntimeMetricsSet());
		metrics.register(MetricsCategory.JVM_OS, new OSMetricsSet());
	}

}
