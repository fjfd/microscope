package com.vipshop.microscope.trace.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.codahale.metrics.MetricRegistry;
import com.vipshop.microscope.trace.stats.Stats;

public class StatsTest {
	
	@Test
    public void testCounter() throws InterruptedException {
		Queue<String> queue = new LinkedList<String>();
    	Stats.inc(name(StatsTest.class, "pedding-jobs"));
    	Stats.inc(MetricRegistry.name(StatsTest.class, "pedding-jobs-new"));
        queue.offer("1");
        
        while(true){
            queue.add("1");
            Thread.sleep(1000);
        }
    }
	
	@Test
	public void testJVMMetrics() throws InterruptedException {
		Stats.statsJVM();
		
		while (true) {
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
