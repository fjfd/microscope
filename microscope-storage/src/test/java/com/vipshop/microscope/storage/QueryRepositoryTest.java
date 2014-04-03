package com.vipshop.microscope.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

public class QueryRepositoryTest {
	
	@Test
	public void testFindExcepList() {
		Map<String, String> query = new HashMap<String, String>();
		
		query.put("appName", "trace");
		query.put("ipAddress", "10.101.3.111");
		
		query.put("limit", "100");
		query.put("startTime", String.valueOf(System.currentTimeMillis() - 1000 * 60 * 60));
		query.put("endTime", String.valueOf(System.currentTimeMillis()));
		
		List<Map<String, Object>> result = QueryRepository.getQueryRepository().findExceptionList(query);
		
		System.out.println(result);
	}
	
	@Test
	public void testFindJVMList() {
		Map<String, String> query = new HashMap<String, String>();
		
		query.put("appName", "trace");
		query.put("ipAddress", "10.101.3.111");
		System.out.println(QueryRepository.getQueryRepository().findJVMList(query));
		
	}
	
	@Test
	public void testFindTop() {
		System.out.println(QueryRepository.getQueryRepository().findTopList());
	}
}
