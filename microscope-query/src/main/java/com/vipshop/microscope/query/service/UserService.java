package com.vipshop.microscope.query.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vipshop.microscope.storage.StorageRepository;

@Service
public class UserService {
	
	private final StorageRepository storageRepository = StorageRepository.getStorageRepository();
	
	public List<Map<String, Object>> findUseHistory() {
		return storageRepository.findUserHistory();
	}
	
}