package com.vipshop.microscope.trace.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.Tracer;

public class ThreadPoolFactory {
	
    public static ExecutorService newFixedThreadPool(int nThreads, Trace context) {
        return Executors.newFixedThreadPool(nThreads, new TraceThreadFactory(context));
    }
    
	static class TraceThreadFactory implements ThreadFactory {
		static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
        final Trace context;
        
        TraceThreadFactory(Trace context) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
                                 Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
            this.context = context;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            
            Tracer.setContext(context);
            return t;
        }
	}
}
