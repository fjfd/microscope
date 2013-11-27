package com.vipshop.microscope.test.app;

import com.vipshop.microscope.hbase.factory.HbaseRepository;

public class HbaseReinitApp {
	
	public static void execute() {
		HbaseRepository.drop();
		HbaseRepository.init();
	}
}
