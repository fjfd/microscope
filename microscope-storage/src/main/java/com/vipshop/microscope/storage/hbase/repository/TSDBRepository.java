package com.vipshop.microscope.storage.hbase.repository;

import org.springframework.stereotype.Repository;

import com.vipshop.microscope.storage.hbase.table.TSDBTable;

@Repository
public class TSDBRepository extends AbstraceRepository {
	
	public void initialize() {
		super.initialize(TSDBTable.TABLE_NAME, TSDBTable.CF_T);
	}
	
	public void drop() {
		super.drop(TSDBTable.TABLE_NAME);
	}
	
}
