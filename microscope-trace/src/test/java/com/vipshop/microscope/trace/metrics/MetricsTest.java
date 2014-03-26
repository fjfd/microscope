package com.vipshop.microscope.trace.metrics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.trace.metrics.MetricsStats;

public class MetricsTest {
	
	@Test
    public void testCounter() throws InterruptedException {
		Queue<String> queue = new LinkedList<String>();
        while(true){
            queue.add("1");
            MetricsStats.inc("queue-size");
            Thread.sleep(1000);
        }
    }
	
	@Test
	public void testJVMMetrics() throws InterruptedException {
		MetricsStats.statsJVM();
		
		while (true) {
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
