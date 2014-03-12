package com.vipshop.microscope.trace;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.logentry.Codec;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;

public class TracerPerTest {
	
	
	@Test
	public void testPerfWithCodec() throws InterruptedException {
		final BlockingQueue<LogEntry> queue = new ArrayBlockingQueue<LogEntry>(10000); 
		
		long starttime = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			queue.offer(Codec.encodeToLogEntry(new Span()));
		}
		
		long endtime = System.currentTimeMillis();
		
		System.out.println(endtime - starttime);
	}
	
	@Test
	public void testPerfWithoutCodec() throws InterruptedException {
		final BlockingQueue<Span> queue = new ArrayBlockingQueue<Span>(10000); 
		
		long starttime = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			queue.offer(new Span());
		}
		
		long endtime = System.currentTimeMillis();
		
		System.out.println(endtime - starttime);

	}

}
