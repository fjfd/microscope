package com.vipshop.microscope.collector.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * A thread worker analyze span. 
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class AnalyzeWorker implements Runnable {
	
	private final MessageAnalyzer analyzer = new MessageAnalyzer();
	private final LinkedBlockingQueue<Span> queue;
	
	public AnalyzeWorker(LinkedBlockingQueue<Span> queue) {
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
					TimeUnit.MILLISECONDS.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
