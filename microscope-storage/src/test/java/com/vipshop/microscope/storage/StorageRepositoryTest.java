package com.vipshop.microscope.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.storage.opentsdb.core.Aggregator;
import com.vipshop.microscope.storage.opentsdb.core.Aggregators;
import com.vipshop.microscope.storage.opentsdb.core.DataPoints;

public class StorageRepositoryTest {
	
	StorageRepository storageRepository = StorageRepository.getStorageRepository();
	
	@Test
	public void testReInitalizeHbaseTable() {
		storageRepository.reInitalizeHbaseTable();
	}
	
	@Test
	public void dropTable() {
		storageRepository.dropHbaseTable();
	}
	
	@Test
	public void createTable() {
		storageRepository.createHbaseTable();
	}
	
	@Test
	public void testSuggestMetrics() {
		System.out.println(storageRepository.suggestMetrics("jvm"));
	}
	
	@Test
	public void testMetrics() throws InterruptedException {
		long timestamp = System.currentTimeMillis();
		String metric = "jvm_memory.Usage";
		Map<String, String> tags = new HashMap<String, String>();
		tags.put("APP", "trace");
		tags.put("IP", "10.101.3.111");
		long value = 10;
		
		storageRepository.add(metric, timestamp, value, tags);
		
		Aggregator function = Aggregators.MAX;
		boolean rate = true;
		
		DataPoints[] dataPoints = storageRepository.find(timestamp - 60 * 1000 * 60, timestamp, metric, tags, function, rate);
		TimeUnit.SECONDS.sleep(1);
		for (int i = 0; i < dataPoints.length; i++) {
			System.out.println(dataPoints[i].toString());
		}
		
	}
	
	@Test
	public void testFindExceptionList() {
		Map<String, String> query = new HashMap<String, String>();
		
		query.put("appName", "trace");
		query.put("ipAddress", "10.101.3.111");
		
		query.put("limit", "100");
		query.put("startTime", String.valueOf(System.currentTimeMillis() - 1000 * 60 * 60));
		query.put("endTime", String.valueOf(System.currentTimeMillis()));
		
		List<Map<String, Object>> result = storageRepository.findExceptionList(query);
		
		System.out.println(result);
	}
	
	@Test
	public void testFindTop() {
		System.out.println(storageRepository.findTopList());
	}

}
