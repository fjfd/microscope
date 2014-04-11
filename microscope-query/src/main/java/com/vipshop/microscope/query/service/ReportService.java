package com.vipshop.microscope.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vipshop.microscope.common.logentry.Constants;
import org.springframework.stereotype.Service;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.opentsdb.core.Aggregator;
import com.vipshop.microscope.storage.opentsdb.core.Aggregators;

@Service
public class ReportService {
	
	private final StorageRepository storageRepository = StorageRepository.getStorageRepository();
	
	public List<Map<String, Object>> metricIndex() {
		return storageRepository.findMetricIndex();
	}
	
	public List<Map<String, Object>> metric(HttpServletRequest request) {
        Map<String, String> query = new HashMap<String, String>();

        query.put(Constants.APP, request.getParameter(Constants.APP));
        query.put(Constants.IP, request.getParameter(Constants.IP));
        query.put(Constants.METRICS, request.getParameter(Constants.METRICS));
        query.put(Constants.STARTTIME, request.getParameter(Constants.STARTTIME));
        query.put(Constants.ENDTIME, request.getParameter(Constants.ENDTIME));

		return storageRepository.findMetric(query);
	}

	public Map<String, Object> getTopReport() {
		return storageRepository.findTopList();
	}
	
}
