package com.vipshop.microscope.trace.metrics.jvm;

import static com.codahale.metrics.MetricRegistry.name;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.jvm.ThreadDeadlockDetector;

public class ThreadMetricsSet implements MetricSet {

	private final ThreadMXBean threads;
	private final ThreadDeadlockDetector deadlockDetector;

	/**
	 * Creates a new set of gauges using the default MXBeans.
	 */
	public ThreadMetricsSet() {
		this(ManagementFactory.getThreadMXBean(), new ThreadDeadlockDetector());
	}

	/**
	 * Creates a new set of gauges using the given MXBean and detector.
	 * 
	 * @param threads
	 *            a thread MXBean
	 * @param deadlockDetector
	 *            a deadlock detector
	 */
	public ThreadMetricsSet(ThreadMXBean threads, ThreadDeadlockDetector deadlockDetector) {
		this.threads = threads;
		this.deadlockDetector = deadlockDetector;
	}

	@Override
	public Map<String, Metric> getMetrics() {
		final ThreadInfo[] allThreads = threads.getThreadInfo(threads.getAllThreadIds());
		final Map<String, Metric> gauges = new LinkedHashMap<String, Metric>();

		for (final Thread.State state : Thread.State.values()) {
			gauges.put(name(state.toString().toLowerCase(), "count"), new Gauge<Object>() {
				@Override
				public Object getValue() {
					return getThreadCount(state, allThreads);
				}
			});
		}

		gauges.put("Count", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return threads.getThreadCount();
			}
		});

		gauges.put("DaemonCount", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return threads.getDaemonThreadCount();
			}
		});

		gauges.put("DeadLocks", new Gauge<Set<String>>() {
			@Override
			public Set<String> getValue() {
				return deadlockDetector.getDeadlockedThreads();
			}
		});

//		gauges.put("ThreadDump", new Gauge<List<String>>() {
//			List<String> threadInfos = new ArrayList<String>();
//			@Override
//			public List<String> getValue() {
//				for (ThreadInfo threadInfo : allThreads) {
//					threadInfos.add(threadInfo.toString());
//				}
//				return threadInfos;
//			}
//		});

		return gauges;
	}

	private int getThreadCount(Thread.State state, ThreadInfo[] allThreads) {
		int count = 0;
		for (ThreadInfo info : allThreads) {
			if (info != null && info.getThreadState() == state) {
				count++;
			}
		}
		return count;
	}
	
}