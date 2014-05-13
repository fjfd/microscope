package com.vipshop.microscope.query.service;

import com.vipshop.microscope.client.exception.ExceptionData;
import com.vipshop.microscope.common.cons.Constants;
import com.vipshop.microscope.storage.StorageRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        String ipAddress = request.getParameter("ipAddress");
        String name = request.getParameter("name");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String limit = request.getParameter("limit");

        Map<String, String> query = new HashMap<String, String>();
        query.put(Constants.APP, appName);
        query.put(Constants.IP, ipAddress);
        query.put(Constants.NAME, name);
        query.put(Constants.STARTTIME, startTime);
        query.put(Constants.ENDTIME, endTime);
        query.put(Constants.LIMIT, limit);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        List<ExceptionData> data = storageRepository.findExceptionData(query);
        for (ExceptionData exceptionData : data) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Constants.APP, exceptionData.getApp());
            map.put(Constants.IP, exceptionData.getIp());
            map.put(Constants.EXCEPTION_MESSAGE, exceptionData.getExceptionMsg());
            map.put(Constants.EXCEPTION_NAME, exceptionData.getExceptionName());
            map.put(Constants.EXCEPTION_STACK, exceptionData.getExceptionStack());
            map.put(Constants.TRACE_ID, exceptionData.getTraceId());
            map.put(Constants.THREAD_INFO, exceptionData.getThreadInfo());
            map.put(Constants.DATE, exceptionData.getDate());
            map.put(Constants.DEBUG, exceptionData.getDebug());
            result.add(map);
        }

        return result;

    }
}
