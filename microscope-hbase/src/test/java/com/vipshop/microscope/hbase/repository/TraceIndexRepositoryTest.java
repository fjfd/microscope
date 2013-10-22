package com.vipshop.microscope.hbase.repository;

import org.testng.annotations.Test;

import com.vipshop.microscope.hbase.repository.Repositorys;

public class TraceIndexRepositoryTest {
	
	@Test
	public void test() {
		System.out.println(Repositorys.TRAC_INDEX.findByAppName("picket"));;
		
	}
}
