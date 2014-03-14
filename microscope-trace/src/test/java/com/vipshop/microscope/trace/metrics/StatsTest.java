package com.vipshop.microscope.trace.metrics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.trace.Tracer;
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
	
	@Test
	public void testStatsQueue() throws InterruptedException {
		Stats.statsQueue();
		
		while (true) {
			Tracer.cleanContext();
			Tracer.clientSend("http://www.huohu.com", Category.URL);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				Tracer.clientSend("getNew@newService", Category.Service);
				TimeUnit.MILLISECONDS.sleep(400);
				Tracer.clientSend("get@DB", Category.DB);
				TimeUnit.MILLISECONDS.sleep(100);
				Tracer.clientReceive();
				Tracer.clientReceive();
				
				Tracer.clientSend("buyNew@buyService", Category.Service);
				TimeUnit.MILLISECONDS.sleep(200);
				Tracer.clientSend("buy@Cache", Category.Cache);
				TimeUnit.MILLISECONDS.sleep(10);
				Tracer.clientReceive();
				Tracer.clientReceive();
			} catch (Exception e) {
				Tracer.setResultCode(e);
			} finally {
				Tracer.clientReceive();
			}
			
			TimeUnit.SECONDS.sleep(1);
		}
		
	}
}
