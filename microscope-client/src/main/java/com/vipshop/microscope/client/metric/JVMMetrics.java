package com.vipshop.microscope.client.metric;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.RatioGauge;

import java.lang.management.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * @author Xu Fei
 * @version 1.0
 */
public class JVMMetrics implements MetricSet {

    private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");

    private final ClassLoadingMXBean clBean = ManagementFactory.getClassLoadingMXBean();
    private final List<GarbageCollectorMXBean> garbageCollectors = ManagementFactory.getGarbageCollectorMXBeans();
    private final CompilationMXBean cBean = ManagementFactory.getCompilationMXBean();
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
     */
    private final MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
    private final List<MemoryPoolMXBean> memoryPools = ManagementFactory.getMemoryPoolMXBeans();
    private final ThreadMXBean threads = ManagementFactory.getThreadMXBean();
    private final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    /**
     * A map of metric names to metrics.
     *
     * @return the metrics
     */
    @Override
    public Map<String, Metric> getMetrics() {
        final Map<String, Metric> gauges = new LinkedHashMap<String, Metric>();

        gauges.put("count.loaded", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return clBean.getTotalLoadedClassCount();
            }
        });

        gauges.put("count.unloaded", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return clBean.getUnloadedClassCount();
            }
        });

        gauges.put("count.shared-loaded", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return 0l;
            }
        });

        gauges.put("count.shared-unloaded", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return 0l;
            }
        });

        for (final GarbageCollectorMXBean gc : garbageCollectors) {
            final String name = WHITESPACE.matcher(gc.getName()).replaceAll("-");
            gauges.put(name(name, "count"), new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return gc.getCollectionCount();
                }
            });

            gauges.put(name(name, "time"), new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return gc.getCollectionTime();
                }
            });
        }

        gauges.put(("total-compilation.time"), new Gauge<Long>() {
            @Override
            public Long getValue() {
                return cBean.getTotalCompilationTime();
            }
        });


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
            } else if (pool.getName().equals("Tenured Gen")) {
                name = "tenured-gen.";
            } else if (pool.getName().equals("Eden Space")) {
                name = "eden-space.";
            } else if (pool.getName().equals("Perm Gen")) {
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
                public Long getValue() {
                    return pool.getUsage().getCommitted();
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

        final ThreadInfo[] allThreads = threads.getThreadInfo(threads.getAllThreadIds());

        for (final Thread.State state : Thread.State.values()) {
            gauges.put(name("count", state.toString()), new Gauge<Object>() {
                @Override
                public Object getValue() {
                    return getThreadCount(state, allThreads);
                }
            });
        }

        gauges.put("count.total", new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return threads.getThreadCount();
            }
        });

        gauges.put("count.daemon", new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return threads.getDaemonThreadCount();
            }
        });

        gauges.put("count.peak", new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return threads.getPeakThreadCount();
            }
        });

        gauges.put("count.total-start", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return threads.getTotalStartedThreadCount();
            }
        });


        gauges.put("time.jvm-start", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return runtimeMXBean.getStartTime();
            }
        });

        gauges.put("time.jvm-up", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return runtimeMXBean.getUptime();
            }
        });

        // ***************************** monitor CPU ********************************* //
        gauges.put("system.load-average", new Gauge<Double>() {
            @Override
            public Double getValue() {
                return osBean.getSystemLoadAverage();
            }
        });

        // ***************************** monitor memory ****************************** //
        if (isInstanceOfInterface(osBean.getClass(), "com.sun.management.OperatingSystemMXBean")) {
            final com.sun.management.OperatingSystemMXBean b = (com.sun.management.OperatingSystemMXBean) osBean;

            gauges.put("memory.total-physical", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getTotalPhysicalMemorySize();
                }
            });

            gauges.put("memory.free-physical", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getFreePhysicalMemorySize();
                }
            });

            gauges.put("memory.committed-virtual", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getCommittedVirtualMemorySize();
                }
            });

            // ***************************** monitor disk   ****************************** //
            gauges.put("space.total-swap", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getTotalSwapSpaceSize();
                }
            });

            gauges.put("space.free-swap", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getFreeSwapSpaceSize();
                }
            });

            gauges.put("system.cpu-Time", new Gauge<Long>() {
                @Override
                public Long getValue() {
                    return b.getProcessCpuTime();
                }
            });
        }

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

    private boolean isInstanceOfInterface(Class<?> clazz, String interfaceName) {
        if (clazz == Object.class) {
            return false;
        } else if (clazz.getName().equals(interfaceName)) {
            return true;
        }

        Class<?>[] interfaceclasses = clazz.getInterfaces();

        for (Class<?> interfaceClass : interfaceclasses) {
            if (isInstanceOfInterface(interfaceClass, interfaceName)) {
                return true;
            }
        }

        return isInstanceOfInterface(clazz.getSuperclass(), interfaceName);
    }
}
