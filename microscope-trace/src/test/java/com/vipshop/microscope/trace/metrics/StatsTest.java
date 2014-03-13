package com.vipshop.microscope.trace.metrics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.trace.stats.Stats;

public class StatsTest {
	
	@Test
    public void testCounter() throws InterruptedException {
		Queue<String> queue = new LinkedList<String>();
        while(true){
            queue.add("1");
            Stats.inc(StatsTest.class, "queue-size");
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
