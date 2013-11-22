package com.vipshop.microscope.hbase.repository;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.factory.HbaseFactory;

public class AppRepositoryTest {
	
	@Test
	public void init() {
		HbaseFactory.APP_TRACE.initialize();
	}
	
	@Test
	public void drop() {
		HbaseFactory.APP_TRACE.drop();
	}
	
	@Test
	public void add() {
		AppTrace app = new AppTrace("passport", "action/test/find2");
		HbaseFactory.APP_TRACE.save(app);
		List<Map<String, Object>> result = HbaseFactory.APP_TRACE.findAll();
		for (Map<String, Object> map : result) {
			if (map.containsKey("passport")) {
				Assert.assertEquals(true, map.containsKey("passport"));
			}
		}
		Assert.assertFalse(false);
	}
	
	@Test
	public void testFindAll() {
		HbaseFactory.APP_TRACE.findAll();
	}
}
