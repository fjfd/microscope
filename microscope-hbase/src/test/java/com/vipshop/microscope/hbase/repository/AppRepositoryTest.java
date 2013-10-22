package com.vipshop.microscope.hbase.repository;

import org.testng.annotations.Test;

import com.vipshop.microscope.hbase.domain.AppTrace;

public class AppRepositoryTest {
	
	@Test
	public void init() {
		Repositorys.APP_TRACE.initialize();
	}
	
	@Test
	public void drop() {
		Repositorys.APP_TRACE.drop();
	}
	
	@Test
	public void add() {
		AppTrace app = new AppTrace("passport", "action/test/find2");
		Repositorys.APP_TRACE.save(app);
	}
	
	@Test
	public void testFindAll() {
		System.out.println(Repositorys.APP_TRACE.findAll());
	}
}
