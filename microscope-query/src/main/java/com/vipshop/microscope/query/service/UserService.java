package com.vipshop.microscope.query.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vipshop.microscope.storage.QueryRepository;

@Service
public class UserService {
	
	private final QueryRepository repository = QueryRepository.getQueryRepository();
	
	public List<Map<String, Object>> findUseHistory() {
		return repository.findUserHistory();
	}
	
}