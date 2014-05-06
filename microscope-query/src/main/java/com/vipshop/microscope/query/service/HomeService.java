package com.vipshop.microscope.query.service;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.trace.Constants;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeService {

    private final StorageRepository storageRepository = StorageRepository.getStorageRepository();

    public Map<String, Object> getSystemInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, String> query = new HashMap<String, String>();
        query.put(Constants.APP, request.getParameter(Constants.APP));
        query.put(Constants.IP, request.getParameter(Constants.IP));

        result.put("systeminfo", storageRepository.findSystemData(query));

        return result;
    }

    public List<Map<String, Object>> getQueryCondition() {
        return storageRepository.findExceptionIndex();
    }

}
