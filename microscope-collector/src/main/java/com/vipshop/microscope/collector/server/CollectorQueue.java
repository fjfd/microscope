package com.vipshop.microscope.collector.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.vipshop.microscope.thrift.LogEntry;

public class CollectorQueue {
	
	private static final BlockingQueue<LogEntry> queue = new LinkedBlockingQueue<LogEntry>(Integer.MAX_VALUE);
	
	public static void offer(LogEntry logEntry) {
		queue.offer(logEntry);
	}
	
	public static LogEntry poll() {
		return queue.poll();
	}
	
	public static LogEntry take() throws InterruptedException {
		return queue.take();
	}
	
}
