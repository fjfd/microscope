package com.vipshop.microscope.trace.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.Constant;

/**
 * A queue for store client message.
 * 
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageQueue {
	
	private static long lost = 0;
	
	private static final BlockingQueue<Span> queue = new LinkedBlockingQueue<Span>(Constant.QUEUE_SIZE);
	
	/*
	 * If disconnect, stop collect span to queue.
	 */
	public static void addSpan(Span span) { 
		if(!queue.offer(span)){
			queue.clear();
			lost++;
		}
	}
	
	public static Span poll() {
		return queue.poll();
	}
	
	public static void log() {
		System.out.println(lost);
	}
}
