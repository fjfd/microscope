package com.vipshop.microscope.collector.analyzer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.hbase.domain.StatByType;
import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageAnalyzer {
	
	private static final ConcurrentHashMap<String, StatByType> container = new ConcurrentHashMap<String, StatByType>();
	
	static {
		List<StatByType> result = Repositorys.STAT_TYPE.findAll();
		if (result != null) {
			for (StatByType statByType : result) {
				container.put(statByType.getType(), statByType);
			}
		}
	}
	
	public void analyze(Span span) {
		
		String type = span.getType();
		String resultCode = span.getResultCode();
		int duration = span.getDuration();
		long startTime = span.getStartstamp();
		long endTime = span.getStartstamp() + duration;
		
		StatByType previous = container.get(type);
		// first time 
		if (previous == null) {
			
			previous = new StatByType();
			previous.setType(type);
			previous.setTotalCount(1);
			if (!resultCode.equals("OK")) {
				previous.setFailureCount(1);
				previous.setFailurePrecent(100.00f);
			} else {
				previous.setFailureCount(0);
				previous.setFailurePrecent(0.00f);
			}
			
			previous.setMin(duration);
			previous.setMax(duration);
			previous.setAvg(duration);
			previous.setStartTime(startTime);
			previous.setEndTime(endTime);
			float tps = 1 / ((endTime - startTime) / 1000);
			previous.setTPS(tps);
			
			container.put(type, previous);
			
			Repositorys.STAT_TYPE.save(previous);
			
		} else {
			previous.setTotalCount(previous.getTotalCount() + 1);
			if (!resultCode.equals("OK")) {
				previous.setFailureCount(previous.getFailureCount() + 1);
			} 
			float precent = previous.getFailureCount() / previous.getTotalCount();
			previous.setFailurePrecent(precent);
			
			if (duration < previous.getMin()) {
				previous.setMin(duration);
			}
			
			if (duration < previous.getMax()) {
				previous.setMax(duration);
			}
			
			int avg = (int) ((previous.getAvg() + duration) / 2);
			previous.setAvg(avg);
			
			if (startTime < previous.getStartTime()) {
				previous.setStartTime(startTime);
			}
			
			if (endTime > previous.getEndTime()) {
				previous.setEndTime(endTime);
			}
			
			float tps = previous.getTotalCount() /((previous.getEndTime() - previous.getStartTime()) / 1000); 
			
			previous.setTPS(tps);
			
			container.put(type, previous);
			
			Repositorys.STAT_TYPE.save(previous);
		}
	}
}
