package com.vipshop.microscope.trace;

import com.vipshop.microscope.trace.span.Category;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TracerTest {

    @Test
    public void testTrace() throws InterruptedException {
        Tracer.cleanContext();
        Tracer.clientSend("http://www.huohu.com", Category.URL);
        Tracer.record("URL", "http://10.100.90.183/#/trace");
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
            Tracer.clientSend("getNew@newService", Category.Service);
            Tracer.record("username", "alex");
            Tracer.record("email", "alex.ux01@vipshop.com");
            TimeUnit.MILLISECONDS.sleep(400);
            Tracer.clientSend("get@DB", Category.DB);
            Tracer.record("sql", "select * from user where username = ? and email = ?");
            TimeUnit.MILLISECONDS.sleep(100);
            Tracer.clientReceive();
            Tracer.clientReceive();

            Tracer.clientSend("buyNew@buyService", Category.Service);
            Tracer.record("servicename", "userservice");
            TimeUnit.MILLISECONDS.sleep(200);
            Tracer.clientSend("buy@Cache", Category.Cache);
            Tracer.record("cache", "memcache");
            TimeUnit.MILLISECONDS.sleep(10);
            Tracer.clientReceive();
            Tracer.clientReceive();

            throw new UnsupportedOperationException("this is for test");

        } catch (Exception e) {
            Tracer.setResultCode(e);
        } finally {
            Tracer.clientReceive();
        }
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void testTraceLoop() throws InterruptedException {
        for (; ; ) {
            Tracer.cleanContext();
            Tracer.clientSend("http://www.huohu.com", Category.URL);
            Tracer.record("URL", "http://10.100.90.183/#/trace");
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                Tracer.clientSend("getNew@newService", Category.Service);
                Tracer.record("username", "alex");
                Tracer.record("email", "alex.ux01@vipshop.com");
                TimeUnit.MILLISECONDS.sleep(4);
                Tracer.clientSend("get@DB", Category.DB);
                Tracer.record("sql", "select * from user where username = ? and email = ?");
                TimeUnit.MILLISECONDS.sleep(1);
                Tracer.clientReceive();
                Tracer.clientReceive();

                Tracer.clientSend("buyNew@buyService", Category.Service);
                Tracer.record("servicename", "userservice");
                TimeUnit.MILLISECONDS.sleep(2);
                Tracer.clientSend("buy@Cache", Category.Cache);
                Tracer.record("cache", "memcache");
                TimeUnit.MILLISECONDS.sleep(1);
                Tracer.clientReceive();
                Tracer.clientReceive();

            } catch (Exception e) {
                Tracer.setResultCode(e);
            } finally {
                Tracer.clientReceive();
            }
            TimeUnit.SECONDS.sleep(0);
        }
    }

    @Test
    public void testOSP() throws InterruptedException {
        // mainTask clientSend
        Tracer.clientSend("MainTask", Category.Method);
        String traceId = Tracer.getTraceId();
        String spanId = Tracer.getSpanId();

        // mainTask call taskA
        CountDownLatch taskA1CountDownLatch = new CountDownLatch(1);
        TaskA taskA = new TaskA(1, taskA1CountDownLatch);
        taskA.send(traceId, spanId);
        taskA1CountDownLatch.await();

        CountDownLatch taskA2CountDownLatch = new CountDownLatch(1);
        TaskA taskA2 = new TaskA(2, taskA2CountDownLatch);
        taskA2.send(traceId, spanId);
        taskA2CountDownLatch.await();
        Tracer.clientReceive();

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void testTraceClient() throws InterruptedException {
        while (true) {
            Tracer.cleanContext();
            Tracer.clientSend("http://www.huohu.com", Category.URL);
            Tracer.record("URL", "http://10.100.90.183/#/trace");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                Tracer.clientSend("getNew@newService", Category.Service);
                Tracer.record("username", "alex");
                Tracer.record("email", "alex.ux01@vipshop.com");
                TimeUnit.MILLISECONDS.sleep(400);
                Tracer.clientSend("get@DB", Category.DB);
                Tracer.record("sql", "select * from user where username = ? and email = ?");
                TimeUnit.MILLISECONDS.sleep(100);
                Tracer.clientReceive();
                Tracer.clientReceive();

                Tracer.clientSend("buyNew@buyService", Category.Service);
                Tracer.record("servicename", "userservice");
                TimeUnit.MILLISECONDS.sleep(200);
                Tracer.clientSend("buy@Cache", Category.Cache);
                Tracer.record("cache", "memcache");
                TimeUnit.MILLISECONDS.sleep(10);
                Tracer.clientReceive();
                Tracer.clientReceive();

                throw new IllegalAccessException();
            } catch (Exception e) {
                Tracer.setResultCode(e);
            } finally {
                Tracer.clientReceive();
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }

    static class TaskB implements Runnable {
        CountDownLatch taskBCountDownLatch = null;
        String traceId;
        String spanId;
        int step;

        public TaskB(int step, CountDownLatch taskBCountDownLatch) {
            this.taskBCountDownLatch = taskBCountDownLatch;
            this.step = step;
        }

        public void send(String traceId, String spanId) {
            this.traceId = traceId;
            this.spanId = spanId;

            Thread thread = new Thread(this);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            Tracer.clientSend(traceId, spanId, "TaskB_" + step, Category.Method);
            this.traceId = Tracer.getTraceId();
            this.spanId = Tracer.getSpanId();
            Tracer.clientReceive();
            taskBCountDownLatch.countDown();
        }
    }

    static class TaskA implements Runnable {
        String traceId;
        String spanId;
        int step;
        CountDownLatch mainTaskCountDownLatch;

        public TaskA(int step, CountDownLatch mainTaskCountDownLatch) {
            this.mainTaskCountDownLatch = mainTaskCountDownLatch;
            this.step = step;
        }

        public void send(String traceId, String spanId) {
            this.traceId = traceId;
            this.spanId = spanId;
            Thread thread = new Thread(this);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }

        }

        public void run() {
            Tracer.cleanContext();

            Tracer.clientSend(traceId, spanId, "TaskA_" + step, Category.Method);

            this.traceId = Tracer.getTraceId();
            this.spanId = Tracer.getSpanId();

            CountDownLatch taskBCountDownLatch = new CountDownLatch(1);
            TaskB taskB = new TaskB(step, taskBCountDownLatch);
            taskB.send(traceId, spanId);
            try {
                taskBCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Tracer.clientReceive();

            mainTaskCountDownLatch.countDown();
        }
    }


}
