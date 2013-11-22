package com.vipshop.microscope.trace.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger logger = LoggerFactory.getLogger(MessageQueue.class);
	
	private static final BlockingQueue<Span> queue = new LinkedBlockingQueue<Span>(Constant.QUEUE_SIZE);
	
	/*
	 * If client queue is full, empty queue.
	 */
	public static void addSpan(Span span) { 
		boolean isFull = !queue.offer(span);
		if (isFull) {
			queue.clear();
			logger.info("client queue full, clean queue ... ");
		}
	}
	
	public static Span poll() {
		return queue.poll();
	}
	
}
