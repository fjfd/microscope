package com.vipshop.microscope.hbase.repository;

import org.testng.annotations.Test;

import com.vipshop.microscope.hbase.repository.Repositorys;

public class AppIndexRepositoryTest {
	
	@Test
	public void test() {
		
		
		System.out.println(Repositorys.APP_INDEX.exist("login"));
		
		
	}
}
