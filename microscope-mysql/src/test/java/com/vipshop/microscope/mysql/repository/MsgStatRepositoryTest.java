package com.vipshop.microscope.mysql.repository;

import org.testng.annotations.Test;

public class MsgStatRepositoryTest {
	
	MsgStatRepository msgStatRepository = new MsgStatRepository();
	
	@Test
	public void testExist() {
		System.out.println(msgStatRepository.exist());;
	}
	
	@Test
	public void testSave() {
		msgStatRepository.save(100);
	}
	
	@Test
	public void update() {
		msgStatRepository.update(100);
	}
	
}
