package com.vipshop.microscope.query.service;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.trace.Constants;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final StorageRepository storageRepository = StorageRepository.getStorageRepository();

    public List<Map<String, Object>> metricIndex() {
        return storageRepository.findMetricIndex();
    }

    public Map<String, Object> changeName1(HttpServletRequest request) {
        String app = request.getParameter(Constants.APP);
        String name1 = request.getParameter("name");
        return storageRepository.findName1(app, name1);
    }

    public Map<String, Object> changeName2(HttpServletRequest request) {
        String app = request.getParameter(Constants.APP);
        String name2 = request.getParameter("name");
        return storageRepository.findName2(app, name2);
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
