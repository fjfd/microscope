package com.vipshop.microscope.test.hbase;

import com.vipshop.microscope.hbase.repository.Repositorys;

public class HbaseTable {
	
	public static void main(String[] args) {
		Repositorys.drop();
	}
}
