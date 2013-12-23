package com.vipshop.microscope.collector.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;
import com.vipshop.microscope.collector.server.CollectorServer;

/**
 * A thread worker analyze span. 
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAnalyzeWorker implements Runnable {
	
	private final MessageAnalyzer analyzer = new MessageAnalyzer();
	private final LinkedBlockingQueue<Span> queue;
	
	public MessageAnalyzeWorker(LinkedBlockingQueue<Span> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while (true) {
			Span span = queue.poll();

			if (span != null) {
				analyzer.analyze(span);
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
