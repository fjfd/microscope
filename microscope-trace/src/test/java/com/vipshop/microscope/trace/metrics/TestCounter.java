package com.vipshop.microscope.trace.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.LinkedList;
import java.util.Queue;

import org.testng.annotations.Test;

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

    public void testAdd(String str) throws InterruptedException {
        pendingJobs.inc();
        pendingJobsNew.inc();
        queue.offer(str);
        
        while(true){
            queue.add("1");
            Thread.sleep(1000);
        }
    }

    public String take() {
        pendingJobs.dec();
        pendingJobsNew.dec();
        return queue.poll();
    }

    @Test
    public void testJVM() throws InterruptedException {
    	Stats.registThread();
    	Stats.registMemory();
    	Stats.registGC();
    	
        while(true){
            queue.add("1");
            Thread.sleep(1000);
        }
    }
}
