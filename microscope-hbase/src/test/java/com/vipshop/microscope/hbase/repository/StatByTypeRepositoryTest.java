package com.vipshop.microscope.hbase.repository;

import org.testng.annotations.Test;

import com.vipshop.microscope.hbase.domain.StatByType;

public class StatByTypeRepositoryTest {
	
	@Test
	public void testSave() {
		Repositorys.STAT_TYPE.initialize();
		
		StatByType statByType = new StatByType();
		
		Repositorys.STAT_TYPE.save(statByType);
		
	}
	
	@Test
	public void init() {
		Repositorys.STAT_TYPE.drop();
		Repositorys.STAT_TYPE.initialize();
	}
	
	@Test
	public void findAll() {
		System.out.println(Repositorys.STAT_TYPE.findAll());
	}
}
