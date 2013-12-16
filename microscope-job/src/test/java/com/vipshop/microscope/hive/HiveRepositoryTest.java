package com.vipshop.microscope.hive;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.job.hive.TraceRepository;

public class HiveRepositoryTest {
	
	TraceRepository traceRepository = new TraceRepository();
	
	@Test
	public void testFindAll() {
		System.out.println(traceRepository.findAll());
	}
	
	@Test
	public void testCount() {
		int result = traceRepository.count();
		Assert.assertEquals(10, result);
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
