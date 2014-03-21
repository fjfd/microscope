package com.vipshop.microscope.analyzer.domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.queue.FixedPriorityQueue;
import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.storage.StorageRepository;

/**
 * Top 10 slow report
 * 
 * 10 most slow URL 
 * 10 most slow Action 
 * 10 most slow Service 
 * 10 most slow SQL
 * 10 most slow Cache
 * 10 most slow Method
 * 10 most slow System
 * 
 * This report are stated by 1 minute.
 * 
 * @author Xu Fei
 * @version 1.0
 */

public class TopReport extends AbstraceReport {
	
	private static final Logger logger = LoggerFactory.getLogger(TopReport.class);
	
	private StorageRepository repository = StorageRepository.getStorageRepository();
	
	private ConcurrentHashMap<Category, FixedPriorityQueue<Span>> container;

	private Comparator<Span> comparator = new Comparator<Span>() {
		@Override
		public int compare(Span o1, Span o2) {
			if (o1.getDuration() > o2.getDuration()) {
				return -1;
			} else if (o1.getDuration() == o2.getDuration()) {
				return 0;
			} else {
				return 1;
			}
		}
	};

	public TopReport() {
		this.container = new ConcurrentHashMap<Category, FixedPriorityQueue<Span>>(Category.values().length);
		Category[] categories = Category.values();
		for (int i = 0; i < categories.length; i++) {
			container.put(categories[i], new FixedPriorityQueue<Span>(10, comparator));
		}
	}
	
	/**
	 * Analyze top by span.
	 * 
	 * @param calendarUtil
	 * @param span
	 */
	public void analyze(Span span) {
		logger.info("analyze top report for span --> " + span);
		FixedPriorityQueue<Span> queue = container.get(Category.valueOf(span.getSpanType()));
		queue.add(span);
		writeReport();
	}
	
	public ConcurrentHashMap<Category, FixedPriorityQueue<Span>> getContainer() {
		return container;
	}
	
	private void writeReport() {
		HashMap<String, Object> top = new HashMap<String, Object>();
		for (Entry<Category, FixedPriorityQueue<Span>> entry : container.entrySet()) {
			StringBuilder builder = new StringBuilder();
			PriorityQueue<Span> queue = entry.getValue().getQueue();
			for (Span span : queue) {
				builder.append(span.getAppName()).append("=")
				 	   .append(span.getTraceId()).append("=")
				 	   .append(span.getDuration()).append("=")
				 	   .append(span.getStartTime()).append(";");
			}
			top.put(entry.getKey().getStrValue(), builder.toString());
		}
		repository.saveTop(top);
	}
	
}
