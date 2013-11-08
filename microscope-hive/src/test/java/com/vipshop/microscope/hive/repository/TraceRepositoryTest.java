package com.vipshop.microscope.hive.repository;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TraceRepositoryTest {
	
	TraceRepository traceRepository = new TraceRepository();
	
	@Test
	public void testCount() {
		int result = traceRepository.count();
		Assert.assertEquals(9, result);
	}
	
	@Test
	public void testCreate() {
		traceRepository.create();
	}
	
	@Test
	public void testDrop() {
		traceRepository.drop();
	}
	
	@Test
	public void testCallTime() {
		System.out.println(traceRepository.callTime());;
	}
}
