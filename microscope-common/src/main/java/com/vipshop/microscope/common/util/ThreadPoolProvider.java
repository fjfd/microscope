package com.vipshop.microscope.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolProvider {
	
    public static ExecutorService newFixedThreadPool(int nThreads, String groupName) {
        return Executors.newFixedThreadPool(nThreads, new DefaultThreadFactory(groupName));
    }
    
    public static ExecutorService newSingleThreadExecutor(String groupName) {
    	return Executors.newSingleThreadExecutor(new DefaultThreadFactory(groupName));
    }
    
    public static ExecutorService newSingleDaemonThreadExecutor(String groupName) {
    	return Executors.newSingleThreadExecutor(new DaemonThreadFactory(groupName));
    }
	
	static class DefaultThreadFactory implements ThreadFactory {

		@SuppressWarnings("unused")
		private String groupName;
		
		static final AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

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

		@SuppressWarnings("unused")
		private String groupName;
		
		static final AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

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
