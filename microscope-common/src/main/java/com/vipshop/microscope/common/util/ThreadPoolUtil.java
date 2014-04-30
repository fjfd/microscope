package com.vipshop.microscope.common.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A threadpool util to provide special thread.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ThreadPoolUtil {

    private static ThreadPoolExecutor httpServerExecutor = new ThreadPoolExecutor(5, 10, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public static ThreadPoolExecutor getHttpServerExecutor() {
        return httpServerExecutor;
    }

    public static ExecutorService newFixedThreadPool(int nThreads, String groupName) {
        return Executors.newFixedThreadPool(nThreads, new DefaultThreadFactory(groupName));
    }

    public static ExecutorService newSingleThreadExecutor(String groupName) {
        return Executors.newSingleThreadExecutor(new DefaultThreadFactory(groupName));
    }

    public static ExecutorService newSingleDaemonThreadExecutor(String groupName) {
        return Executors.newSingleThreadExecutor(new DaemonThreadFactory(groupName));
    }

    public static ScheduledExecutorService newSingleDaemonScheduledThreadPool(String groupName) {
        return Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory(groupName));
    }

    static class DefaultThreadFactory implements ThreadFactory {

        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
        @SuppressWarnings("unused")
        private String groupName;

        public DefaultThreadFactory(String groupName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = groupName + "-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    static class DaemonThreadFactory implements ThreadFactory {

        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
        @SuppressWarnings("unused")
        private String groupName;

        public DaemonThreadFactory(String groupName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = groupName + "-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            t.setDaemon(true);
            t.setPriority(Thread.MIN_PRIORITY);
            return t;
        }
    }


}
