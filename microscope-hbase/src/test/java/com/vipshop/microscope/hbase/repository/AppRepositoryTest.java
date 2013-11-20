package com.vipshop.microscope.hbase.repository;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.hbase.domain.AppTrace;

public class AppRepositoryTest {
	
	@Test
	public void init() {
		HbaseRepository.APP_TRACE.initialize();
	}
	
	@Test
	public void drop() {
		HbaseRepository.APP_TRACE.drop();
	}
	
	@Test
	public void add() {
		AppTrace app = new AppTrace("passport", "action/test/find2");
		HbaseRepository.APP_TRACE.save(app);
		List<Map<String, Object>> result = HbaseRepository.APP_TRACE.findAll();
		for (Map<String, Object> map : result) {
			if (map.containsKey("passport")) {
				Assert.assertEquals(true, map.containsKey("passport"));
			}
		}
		Assert.assertFalse(false);
	}
	
	@Test
	public void testFindAll() {
		HbaseRepository.APP_TRACE.findAll();
	}
}
