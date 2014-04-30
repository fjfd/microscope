package com.vipshop.microscope.query.service;

import com.vipshop.microscope.storage.StorageRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExceptionService {

    private final StorageRepository storageRepository = StorageRepository.getStorageRepository();

    public List<Map<String, Object>> getQueryCondition() {
        return storageRepository.findExceptionIndex();
    }

    public List<Map<String, Object>> getExceptionList(HttpServletRequest request) {
        String appName = request.getParameter("appName");
        String ipAdress = request.getParameter("ipAddress");
        String name = request.getParameter("name");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String limit = request.getParameter("limit");

        Map<String, String> query = new HashMap<String, String>();
        query.put("appName", appName);
        query.put("ipAddress", ipAdress);
        query.put("name", name);
        query.put("startTime", startTime);
        query.put("endTime", endTime);
        query.put("limit", limit);

        return storageRepository.findExceptionList(query);
    }
}
