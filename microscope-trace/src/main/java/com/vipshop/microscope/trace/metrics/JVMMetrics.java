package com.vipshop.microscope.trace.metrics;

import java.io.ByteArrayOutputStream;
import java.lang.management.ManagementFactory;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadDump;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.vipshop.microscope.common.metrics.MetricsCategory;

public class JVMMetrics {

	private static final MetricRegistry metrics = MetricsHolder.getMetricRegistry();

	public static void registerJVM() {
		metrics.register(MetricsCategory.Thread, new ThreadStatesGaugeSet());
		metrics.register(MetricsCategory.Memory, new MemoryUsageGaugeSet());
		metrics.register(MetricsCategory.GC, new GarbageCollectorMetricSet());
	}

	private static final ThreadDump threadDump = new ThreadDump(ManagementFactory.getThreadMXBean());

	static class ThreadDumpGauge implements Gauge<String> {
		@Override
		public String getValue() {
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			threadDump.dump(out);
			return out.toString();
		}
	}
}
