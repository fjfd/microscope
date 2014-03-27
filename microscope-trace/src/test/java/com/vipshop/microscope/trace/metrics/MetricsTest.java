package com.vipshop.microscope.trace.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.vipshop.microscope.trace.metrics.MetricsStats;

public class MetricsTest {
	
    private static Queue<String> queue = new LinkedBlockingDeque<String>();
    
    @BeforeMethod
    public void setUp() {
    	MetricsStats.startSlf4jReporter(1, TimeUnit.SECONDS);
//    	MetricsStats.startMicroscopeReporter();
    }
    
    @Test
    public void testQueueSize() throws InterruptedException {
        MetricsStats.register("queue-size", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return queue.size();
			}
		});
        for (int i = 0; i < 5; i++) {
        	queue.add("1");
        	TimeUnit.SECONDS.sleep(1);
		}
    }
    
	@Test
    public void testCounter() throws InterruptedException {
		Queue<String> queue = new LinkedList<String>();
		for (int i = 0; i < 5; i++) {
            queue.add("1");
            MetricsStats.inc("queue-size");
            Thread.sleep(1000);
        }
    }
	
	@Test
	public void testJVMMetrics() throws InterruptedException {
		MetricsStats.statsJVM();
		for (int i = 0; i < 5; i++) {
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
    @Test
    public void testHistogram() throws InterruptedException {
    	Histogram randomNums = MetricsStats.histogram(name(MetricsTest.class, "random"));
    	Random rand = new Random();
    	for (int i = 0; i < 5; i++) {
            randomNums.update((int) (rand.nextDouble()*100));
            Thread.sleep(1000);
        }
    }
    
    @Test
    public void testMeter() throws InterruptedException {
    	Meter requests = MetricsStats.meter(name(MetricsTest.class, "request"));
    	for (int i = 0; i < 5; i++) {
    		requests.mark();
            Thread.sleep(100);
        }
    }
    
    @Test
    public void testTimer() {
    	Timer requests = MetricsStats.timer(name(MetricsTest.class, "request"));
    	Random random = new Random();
    	for (int i = 0; i < 5; i++) {
            Timer.Context context = requests.time();
            try {
                //some operator
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                context.stop();
            }
        }
    }
}
