package com.vipshop.microscope.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vipshop.microscope.common.logentry.Constants;
import org.springframework.stereotype.Service;

import com.vipshop.microscope.storage.StorageRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class HomeService {
	
	private final StorageRepository storageRepository = StorageRepository.getStorageRepository();

    public Map<String, Object> getSystemInfo(HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, String> query = new HashMap<String, String>();
        query.put(Constants.APP, request.getParameter(Constants.APP));
        query.put(Constants.IP, request.getParameter(Constants.IP));

        result.put("systeminfo", storageRepository.getSystemInfo(query));

        return result;
    }

	public List<Map<String, Object>> getQueryCondition() {
		return storageRepository.findExceptionIndex();
	}

}
