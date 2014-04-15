package com.vipshop.microscope.trace.metrics.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.RatioGauge;

/**
 * Memory metrics
 * 
 * Total
 * Heap 
 * EdenSpace
 * TenuredGen
 * SurvivorSpace
 * Non Heap
 * CodeCache
 * PermGen
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MemeoryMetricsSet implements MetricSet {

	private final MemoryMXBean mxBean;
	private final List<MemoryPoolMXBean> memoryPools;

	public MemeoryMetricsSet() {
		this(ManagementFactory.getMemoryMXBean(), ManagementFactory.getMemoryPoolMXBeans());
	}

	public MemeoryMetricsSet(MemoryMXBean mxBean, Collection<MemoryPoolMXBean> memoryPools) {
		this.mxBean = mxBean;
		this.memoryPools = new ArrayList<MemoryPoolMXBean>(memoryPools);
	}

	@Override
	public Map<String, Metric> getMetrics() {
		final Map<String, Metric> gauges = new LinkedHashMap<String, Metric>();

		gauges.put("Total.Init", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getInit() + mxBean.getNonHeapMemoryUsage().getInit();
			}
		});

		gauges.put("Total.Used", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getUsed() + mxBean.getNonHeapMemoryUsage().getUsed();
			}
		});

		gauges.put("Total.Max", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getMax() + mxBean.getNonHeapMemoryUsage().getMax();
			}
		});

		gauges.put("Total.Committed", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getCommitted() + mxBean.getNonHeapMemoryUsage().getCommitted();
			}
		});

		gauges.put("Heap.Init", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getInit();
			}
		});

		gauges.put("Heap.Used", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getUsed();
			}
		});

		gauges.put("Heap.Max", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getMax();
			}
		});

		gauges.put("Heap.Committed", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getCommitted();
			}
		});

		gauges.put("HeapUsage", new RatioGauge() {
			@Override
			protected Ratio getRatio() {
				final MemoryUsage usage = mxBean.getHeapMemoryUsage();
				return Ratio.of(usage.getUsed(), usage.getMax());
			}
		});

		gauges.put("NonHeap.Init", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getNonHeapMemoryUsage().getInit();
			}
		});

		gauges.put("NonHeap.Used", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getNonHeapMemoryUsage().getUsed();
			}
		});

		gauges.put("NonHeap.Max", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getNonHeapMemoryUsage().getMax();
			}
		});

		gauges.put("NonHeap.Committed", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getNonHeapMemoryUsage().getCommitted();
			}
		});

		gauges.put("NonHeap.Usage", new RatioGauge() {
			@Override
			protected Ratio getRatio() {
				final MemoryUsage usage = mxBean.getNonHeapMemoryUsage();
				return Ratio.of(usage.getUsed(), usage.getMax());
			}
		});

		for (final MemoryPoolMXBean pool : memoryPools) {
			
			String name = "";
			
			if (pool.getName().equals("Code Cache")) {
				name = "CodeCache.";
			} else if (pool.getName().equals("Survivor Space")) {
				name = "SurvivorSpace.";
			}else if (pool.getName().equals("Tenured Gen")) {
				name = "TenuredGen.";
			}else if (pool.getName().equals("Eden Space")) {
				name = "EdenSpace.";
			}else if (pool.getName().equals("Perm Gen")) {
				name = "PermGen.";
			}
			
			gauges.put(name +  "Usage", new RatioGauge() {
				@Override
				protected Ratio getRatio() {
					final long max = pool.getUsage().getMax() == -1 ? pool.getUsage().getCommitted() : pool.getUsage().getMax();
					return Ratio.of(pool.getUsage().getUsed(), max);
				}
			});
			
			gauges.put(name + "Max", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return pool.getUsage().getMax();
				}
			});
			gauges.put(name + "Init", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return pool.getUsage().getInit();
				}
			});
			gauges.put(name + "Committed", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return pool.getUsage().getCommitted();
				}
			});
			gauges.put(name + "Used", new Gauge<Long>() {
				@Override
				public Long getValue() {
					return pool.getUsage().getUsed();
				}
			});
			
		}
		return Collections.unmodifiableMap(gauges);
	}
}
