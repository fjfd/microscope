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
 * Total           memory  [init, used, max, committed]
 * Heap            memory  [init, used, max, committed, usage]
 * Non Heap        memory  [init, used, max, committed, usage]
 * EdenSpace       memory  [init, used, max, committed, usage]
 * TenuredGen      memory  [init, used, max, committed, usage]
 * SurvivorSpace   memory  [init, used, max, committed, usage]
 * CodeCache       memory  [init, used, max, committed, usage]
 * PermGen         memory  [init, used, max, committed, usage]
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

		gauges.put("total.init", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getInit() + mxBean.getNonHeapMemoryUsage().getInit();
			}
		});

		gauges.put("total.used", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getUsed() + mxBean.getNonHeapMemoryUsage().getUsed();
			}
		});

		gauges.put("total.max", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getMax() + mxBean.getNonHeapMemoryUsage().getMax();
			}
		});

		gauges.put("total.committed", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getCommitted() + mxBean.getNonHeapMemoryUsage().getCommitted();
			}
		});

		gauges.put("heap.init", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getInit();
			}
		});

		gauges.put("heap.used", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getUsed();
			}
		});

		gauges.put("heap.max", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getMax();
			}
		});

		gauges.put("heap.committed", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getHeapMemoryUsage().getCommitted();
			}
		});

		gauges.put("heap.usage", new RatioGauge() {
			@Override
			protected Ratio getRatio() {
				final MemoryUsage usage = mxBean.getHeapMemoryUsage();
				return Ratio.of(usage.getUsed(), usage.getMax());
			}
		});

		gauges.put("non-heap.init", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getNonHeapMemoryUsage().getInit();
			}
		});

		gauges.put("non-heap.used", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getNonHeapMemoryUsage().getUsed();
			}
		});

		gauges.put("non-heap.max", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getNonHeapMemoryUsage().getMax();
			}
		});

		gauges.put("non-heap.committed", new Gauge<Long>() {
			@Override
			public Long getValue() {
				return mxBean.getNonHeapMemoryUsage().getCommitted();
			}
		});

		gauges.put("non-heap.usage", new RatioGauge() {
			@Override
			protected Ratio getRatio() {
				final MemoryUsage usage = mxBean.getNonHeapMemoryUsage();
				return Ratio.of(usage.getUsed(), usage.getMax());
			}
		});

		for (final MemoryPoolMXBean pool : memoryPools) {
			
			String name = "";
			
			if (pool.getName().equals("Code Cache")) {
				name = "code-cache.";
			} else if (pool.getName().equals("Survivor Space")) {
				name = "survivor-space.";
			}else if (pool.getName().equals("Tenured Gen")) {
				name = "tenured-gen.";
			}else if (pool.getName().equals("Eden Space")) {
				name = "eden-space.";
			}else if (pool.getName().equals("Perm Gen")) {
				name = "perm-gen.";
			}

            gauges.put(name + "max", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return pool.getUsage().getMax();
                }
            });
            gauges.put(name + "init", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return pool.getUsage().getInit();
                }
            });
            gauges.put(name + "committed", new Gauge<Long>() {
                @Override
                public Long getValue() {return pool.getUsage().getCommitted();
                }
            });
            gauges.put(name + "used", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return pool.getUsage().getUsed();
                }
            });
            gauges.put(name + "usage", new RatioGauge() {
                @Override
                protected Ratio getRatio() {
                final long max = pool.getUsage().getMax() == -1 ? pool.getUsage().getCommitted() : pool.getUsage().getMax();
                return Ratio.of(pool.getUsage().getUsed(), max);
                }
            });

		}
		return Collections.unmodifiableMap(gauges);
	}
}
