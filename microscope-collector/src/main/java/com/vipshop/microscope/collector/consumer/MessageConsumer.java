package com.vipshop.microscope.collector.consumer;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.analyzer.MessageAnalyzers;
import com.vipshop.microscope.collector.processor.MessageProcessors;
import com.vipshop.microscope.collector.server.CollectorQueue;
import com.vipshop.microscope.thrift.LogEntry;

public class MessageConsumer implements Runnable {
	
	@Override
	public void run() {
		while (true) {
			LogEntry logEntry = CollectorQueue.poll();
			if (logEntry != null) {
				MessageProcessors.process(logEntry);
				MessageAnalyzers.analyze(logEntry);
			} else {
				try {
					TimeUnit.MILLISECONDS.sleep(3);
				} catch (InterruptedException e) {
					
				}
			}
		}
	}
	
}
