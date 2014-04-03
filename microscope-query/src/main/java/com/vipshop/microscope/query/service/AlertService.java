package com.vipshop.microscope.query.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vipshop.microscope.storage.QueryRepository;

@Service
public class AlertService {
	
	private final QueryRepository queryRepository = QueryRepository.getQueryRepository();
	
	public List<Map<String, Object>> getQueryCondition() {
		return queryRepository.findExceptionIndex();
	}

}
