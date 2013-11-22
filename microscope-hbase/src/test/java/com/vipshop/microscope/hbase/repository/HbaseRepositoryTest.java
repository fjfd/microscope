package com.vipshop.microscope.hbase.repository;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.factory.HbaseRepository;

public class HbaseRepositoryTest {

	public static final Logger logger = LoggerFactory.getLogger(HbaseRepositoryTest.class);
	
	@Test
	public void reinit() {
		HbaseRepository.drop();
		HbaseRepository.init();
	}
	
	@Test
	public void init() {
		HbaseRepository.init();
	}
	
	@Test
	public void drop() throws IOException {
		HbaseRepository.drop();
	}
	
	@Test
	public void saveAppAndTrace() {
		AppTrace app = new AppTrace("passport", "action/test/find2");
		HbaseRepository.save(app);
	}
	
}
