package com.vipshop.microscope.trace.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.Constant;
import com.vipshop.microscope.trace.thrift.ThriftClient;

/**
 * A queue for store client message.
 * 
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageQueue {
	
	private static final BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(Constant.QUEUE_SIZE);
	
	/*
	 * If disconnect, stop collect span to queue.
	 */
	public static void addSpan(Span span) { 
		if (ThriftClient.isConnect()) {
			queue.offer(span);
		}
	}
	
	public static Object poll() {
		return queue.poll();
	}
}
