package com.vipshop.microscope.query.service;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.common.cons.Constants;
import net.opentsdb.core.Aggregators;
import net.opentsdb.core.DataPoints;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    private final StorageRepository storageRepository = StorageRepository.getStorageRepository();

    public Map<String, Object> metric(HttpServletRequest request) {

        /**
         * start time
         */
        long startTime = Long.valueOf(request.getParameter(Constants.STARTTIME));

        /**
         * end time
         */
        long endTime = 0L;
        if (request.getParameter(Constants.ENDTIME) == null) {
            endTime = System.currentTimeMillis();
        } else {
            endTime = Long.valueOf(request.getParameter(Constants.ENDTIME));
        }

        /**
         * metric name
         */
        String metric = request.getParameter(Constants.METRIC);

        /**
         * tags
         */
        Map<String, String> tags = new HashMap<String, String>();
        tags.put(Constants.APP, request.getParameter(Constants.APP));
        tags.put(Constants.IP, request.getParameter(Constants.IP));

        Map<String, Object> result = new HashMap<String, Object>();

        DataPoints[] dataPointes = storageRepository.find(startTime, endTime, metric, tags, Aggregators.AVG, false);
        for(int i = 0; i < dataPointes.length; i++) {
            result.put("dataPoints[" + i + "]", dataPointes[i].toString());
        }

        return result;
    }

    public Map<String, Object> getTopReport() {
        return null;
    }


}
