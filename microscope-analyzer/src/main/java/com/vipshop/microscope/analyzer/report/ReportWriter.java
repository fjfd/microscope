package com.vipshop.microscope.analyzer.report;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.analyzer.domain.TopReport;
import com.vipshop.microscope.common.queue.FixedPriorityQueue;
import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.storage.StorageRepository;

public class ReportWriter {
	
	private ScheduledExecutorService executor = ThreadPoolUtil.newSingleDaemonScheduledThreadPool("report-writer-pool");
	
	private StorageRepository repository = StorageRepository.getStorageRepository();
	
	private TopReport topReport;
	
	public ReportWriter(TopReport topReport) {
		this.topReport = topReport;
	}
	
	public void start() {
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				writeTopReport();
			}
		}, 0, 60 , TimeUnit.SECONDS);
	}
	
	private void writeTopReport() {
		ConcurrentHashMap<Category, FixedPriorityQueue<Span>> queueMap = topReport.getContainer();
		HashMap<String, Object> top = new HashMap<String, Object>();
		for (Entry<Category, FixedPriorityQueue<Span>> entry : queueMap.entrySet()) {
			StringBuilder builder = new StringBuilder();
			PriorityQueue<Span> queue = entry.getValue().getQueue();
			for (Span span : queue) {
				builder.append(span.getAppName()).append("=")
				 	   .append(span.getTraceId()).append("=")
				 	   .append(span.getStartTime());
			}
			top.put(entry.getKey().getStrValue(), builder.toString());
		}
		repository.saveTop(top);
	}
}
