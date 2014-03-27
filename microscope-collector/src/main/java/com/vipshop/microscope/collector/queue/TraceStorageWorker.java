package com.vipshop.microscope.collector.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.CollectorServer;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.trace.Span;

/**
 * A thread worker store span.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceStorageWorker implements Runnable {
	
	private final MessageStorager storager = MessageStorager.getMessageStorager();
	private final LinkedBlockingQueue<Span> queue;
	
	public TraceStorageWorker(LinkedBlockingQueue<Span> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while (true) {
			Span span = queue.poll();

			if (span != null) {
				storager.storageTrace(span);
			} else {
				try {
					TimeUnit.MILLISECONDS.sleep(CollectorServer.SLEEP_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
