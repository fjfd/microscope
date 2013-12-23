package com.vipshop.micorscope.framework.http;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 描述：线程池<br>
 *     实现单例
 *
 * @author: dashu
 * @since: 13-3-1
 */
public class ThreadPool extends  ThreadPoolExecutor{

    private static ThreadPool threadPool;

    private ThreadPool(){
        super(5,10, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public synchronized static ThreadPool getInstance() {
        if (threadPool == null) {
            threadPool = new ThreadPool();
        }
        return threadPool;
    }

}
