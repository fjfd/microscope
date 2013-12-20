package com.vipshop.microscope.thrift.perf;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.TException;
import org.testng.annotations.Test;

import com.vipshop.microscope.thrift.client.ThriftClient;
import com.vipshop.microscope.thrift.gen.LogEntry;
import com.vipshop.microscope.thrift.server.ThriftCategory;

public class ThriftPerformanceTest {
	
	@Test
	public void testSimpleThriftServer() throws TException {
		LogEntry logEntry = new LogEntry("test", "message");
		ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.SIMPLE);
		long start = System.currentTimeMillis();
		
		int size = 10000;
		for (int i = 0; i < size; i++) {
			client.send(Arrays.asList(logEntry));
		}
		
		long end = System.currentTimeMillis();

		System.out.println("thoughput " + size * 1000/(end - start));
	}
	
	@Test
	public void testSimpleThriftServerMultiClient() throws TException, InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(10);
		
		long start = System.currentTimeMillis();
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		final int size = 10000;
		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.SIMPLE);
				LogEntry logEntry = new LogEntry("test", "message");
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int i = 0; i < size; i++) {
						client.send(Arrays.asList(logEntry));
					}
					countDownLatch.countDown();
				}
			});
		}
		
		countDownLatch.await();
		long end = System.currentTimeMillis();

		System.out.println("thoughput " + size * 1000 * 10/(end - start));
	}

	
	@Test
	public void testNonBlockingThriftServer() throws TException {
		LogEntry logEntry = new LogEntry("test", "message");

		ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.NON_BLOCKING);
		long start = System.currentTimeMillis();
		
		int size = 10000;
		for (int i = 0; i < size; i++) {
			client.send(Arrays.asList(logEntry));
		}
		
		long end = System.currentTimeMillis();

		System.out.println("thoughput " + size * 1000/(end - start));
	}
	
	@Test
	public void testNonBlockingThriftServerMultiClient() throws InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(10);
		
		long start = System.currentTimeMillis();
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		final int size = 10000;
		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.NON_BLOCKING);
				LogEntry logEntry = new LogEntry("test", "message");
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int i = 0; i < size; i++) {
						client.send(Arrays.asList(logEntry));
					}
					countDownLatch.countDown();
				}
			});
		}
		
		countDownLatch.await();
		long end = System.currentTimeMillis();

		System.out.println("thoughput " + size * 1000/(end - start));

	}
	
	@Test
	public void testThreadSelectorThriftServer() throws TException {
		List<LogEntry> logEntries = Arrays.asList(new LogEntry("test", "message"));

		ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.THREAD_SELECTOR);
		long start = System.currentTimeMillis();
		
		int size = 10000;
		for (int i = 0; i < size; i++) {
			client.send(logEntries);
		}
		
		long end = System.currentTimeMillis();

		System.out.println("thoughput " + size * 1000/(end - start));
	}
	
	@Test
	public void testThreadSelectorThriftServerMultiClient() throws TException, InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(10);
		
		long start = System.currentTimeMillis();
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		final int size = 10000;
		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.THREAD_SELECTOR);
				LogEntry logEntry = new LogEntry("test", "message");
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int i = 0; i < size; i++) {
						client.send(Arrays.asList(logEntry));
					}
					countDownLatch.countDown();
				}
			});
		}
		
		countDownLatch.await();
		long end = System.currentTimeMillis();

		System.out.println("send " + size + " takes " + (end - start));


	}
	
	@Test
	public void testThreadPoolThriftServer() throws TException, InterruptedException {
		final LogEntry logEntry = new LogEntry("test", "message");

		final CountDownLatch countDownLatch = new CountDownLatch(10);
		long start = System.currentTimeMillis();
		
		
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				
				final ThriftClient client = new ThriftClient("localhost", 9410, 300, ThriftCategory.THREAD_POOL);
				@Override
				public void run() {
					int size = 10000;
					for (int i = 0; i < size; i++) {
						client.send(Arrays.asList(logEntry));
					}
					countDownLatch.countDown();
					
				}
			});
		}
		
		countDownLatch.await();
		long end = System.currentTimeMillis();

		System.out.println("send " + 1000 + " takes " + (end - start));
	}

	
}
