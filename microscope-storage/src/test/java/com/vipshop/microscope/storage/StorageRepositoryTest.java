package com.vipshop.microscope.storage;

import org.testng.annotations.Test;

public class StorageRepositoryTest {
	
	StorageRepository storageRepository = StorageRepository.getStorageRepository();
	
	@Test
	public void testReInitalizeHbaseTable() {
		storageRepository.reInitalizeHbaseTable();
	}
	
	@Test
	public void dropTable() {
		storageRepository.dropHbaseTable();
	}
	
	@Test
	public void createTable() {
		storageRepository.createHbaseTable();
	}
}
