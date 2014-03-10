package com.vipshop.microscope.trace.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.LinkedList;
import java.util.Queue;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.vipshop.microscope.trace.stats.Stats;
/**
 * User: hzwangxx
 * Date: 14-2-14
 * Time: 14:02
 * 测试Counter
 */
public class TestCounter {

    private static Counter pendingJobs = Stats.getCounter(name(TestCounter.class, "pedding-jobs"));
    private static Counter pendingJobsNew = Stats.getCounter(MetricRegistry.name(TestCounter.class, "pedding-jobs-new"));

    private static Queue<String> queue = new LinkedList<String>();

    public static void add(String str) {
        pendingJobs.inc();
        pendingJobsNew.inc();
        queue.offer(str);
    }

    public String take() {
        pendingJobs.dec();
        pendingJobsNew.dec();
        return queue.poll();
    }

    public static void main(String[]args) throws InterruptedException {
    	Stats.registThread();
    	Stats.registMemory();
    	
        while(true){
            add("1");
            Thread.sleep(1000);
        }
    }
}
