package com.vipshop.microscope.mysql.repository;

import org.testng.annotations.Test;

import com.vipshop.microscope.mysql.domain.TraceStat;

public class MySQLTemplateTest {
	
	TraceStatRepository mySQLTemplate = MysqlRepositorys.getTraceStatRepository();
	
	@Test
	public void testCreate() {
		String sql = "CREATE TABLE t1(id int not null,name char(20))";
		mySQLTemplate.create(sql);
	}
	
	@Test
	public void testSave() {
		TraceStat traceStat = new TraceStat();
		traceStat.setName("trace/queryconditon2");
		traceStat.setTotalCount(100);
		traceStat.setFailureCount(10);
		traceStat.setFailurePrecent(1.2f);
		traceStat.setMin(4.2f);
		traceStat.setMax(4234f);
		traceStat.setAvg(232);
		
		mySQLTemplate.save(traceStat);
	}
	
	@Test
	public void testUpdate() {
		TraceStat traceStat = new TraceStat();
		traceStat.setName("trace/queryconditon");
		traceStat.setTotalCount(1000);
		traceStat.setFailureCount(10);
		traceStat.setFailurePrecent(1.2f);
		traceStat.setMin(4.2f);
		traceStat.setMax(4234f);
		traceStat.setAvg(232);
		
		mySQLTemplate.update(traceStat);
	}
	
	@Test
	public void testfind() {
		System.out.println(mySQLTemplate.findTraceStat());;
	}
}
