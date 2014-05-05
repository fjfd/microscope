package com.vipshop.microscope.trace;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.vipshop.microscope.trace.metric.Metrics;
import org.testng.annotations.Test;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

public class MetricsTest {

    @Test
    public void testMicroscopeJVMMetrics() throws InterruptedException {
        Tracer.cleanContext();

        for (; ; ) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void test() {
        throw new IllegalArgumentException("the metrics name you given : [" + "" + "] is bad, please redefine your name \n" +
                "with pattern [jvm.memory.EdenSpace.Max] ");
    }

    @Test
    public void testJVMMetrics() throws InterruptedException {
        Metrics.startSlf4jReporter(1, TimeUnit.SECONDS);
        Metrics.registerJVM();
        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testRegister() throws InterruptedException {
        Metrics.startSlf4jReporter(1, TimeUnit.SECONDS);
        final Queue<String> queue = new LinkedBlockingDeque<String>();
        Metrics.register("queue-size", new Gauge<Integer>() {
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
        Metrics.startSlf4jReporter(1, TimeUnit.SECONDS);
        for (int i = 0; i < 5; i++) {
            Metrics.inc("queue-size");
            Thread.sleep(1000);
        }
    }

    @Test
    public void testHistogram() throws InterruptedException {
        Metrics.startSlf4jReporter(1, TimeUnit.SECONDS);
        Histogram randomNums = Metrics.histogram(name(MetricsTest.class, "random"));
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            randomNums.update((int) (rand.nextDouble() * 100));
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testMeter() throws InterruptedException {
        Metrics.startSlf4jReporter(1, TimeUnit.SECONDS);
        Meter requests = Metrics.meter(name(MetricsTest.class, "request"));
        for (int i = 0; i < 5; i++) {
            requests.mark();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testTimer() throws InterruptedException {
        Metrics.startSlf4jReporter(1, TimeUnit.SECONDS);
        Timer requests = Metrics.timer(name(MetricsTest.class, "request"));
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Timer.Context context = requests.time();
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                context.stop();
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
