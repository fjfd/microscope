package com.vipshop.microscope.trace.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

public class LocalMetriceTest {
	
	private static final MetricRegistry metrics = new MetricRegistry();

	static {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();
		reporter.start(1, TimeUnit.SECONDS);
	}

    private static Queue<String> queue = new LinkedBlockingDeque<String>();
    
    @Test
    public void testSize() throws InterruptedException {
    	Gauge<Integer> gauge = new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return queue.size();
            }
        };
    	
        metrics.register("queue-size", gauge);
    	
    	while (true) {
    		queue.add("1");
			TimeUnit.SECONDS.sleep(1);
		}
    }
    
    @Test
    public void testJVM() throws InterruptedException {
    	metrics.register("Thread", new ThreadStatesGaugeSet());
        metrics.register("Memory", new MemoryUsageGaugeSet());
        metrics.register("File", new FileDescriptorRatioGauge());
        metrics.register("GC", new GarbageCollectorMetricSet());
        
        while (true) {
        	TimeUnit.SECONDS.sleep(1);
		}
    }
    
    @Test
    public void testHistogram() throws InterruptedException {
    	Histogram randomNums = metrics.histogram(name(LocalMetriceTest.class, "random"));
    	Random rand = new Random();
        while(true){
            randomNums.update((int) (rand.nextDouble()*100));
            Thread.sleep(100);
        }
    }
    
    @Test
    public void testMeter() throws InterruptedException {
    	Meter requests = metrics.meter(name(LocalMetriceTest.class, "request"));
    	while(true){
    		requests.mark();
            Thread.sleep(100);
        }
    }
    
    @Test
    public void testTimer() {
    	Timer requests = metrics.timer(name(LocalMetriceTest.class, "request"));
    	Random random = new Random();
        while(true){
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
