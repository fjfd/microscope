package com.vipshop.microscope.collector.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * A thread worker store span.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageStorageWorker implements Runnable {
	
	private final MessageStorager storager = new MessageStorager();
	private final LinkedBlockingQueue<Span> queue;
	
	public MessageStorageWorker(LinkedBlockingQueue<Span> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while (true) {
			Span span = queue.poll();

			if (span != null) {
				storager.storage(span);
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
