package com.vipshop.microscope.query.service;

import java.util.ArrayList;
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

    public Map<String, Object> reportIndex() {
        Map<String, Object> result = new HashMap<String, Object>();

        List<String> report = new ArrayList<String>();

        report.add("httpclient");
        report.add("jvm");
        report.add("servlet");

        result.put("name", report);
        return result;
    }

    public Map<String, Object> reportLevel2Index(HttpServletRequest request) {
        String name = request.getParameter("name");

        Map<String, Object> result = new HashMap<String, Object>();

        List<String> report = new ArrayList<String>();

        if (name.equals("httpclient")) {
            report.add("httpclient.get");
            report.add("httpclient.pool");
        }

        if (name.equals("jvm")) {
            report.add("monitor");
            report.add("gc");
            report.add("memory");
            report.add("thread");
        }

        if (name.equals("servlet")) {
            report.add("servlet.code");
            report.add("request");
            report.add("active.request");
        }


        result.put("name", report);
        return result;
    }


    public Map<String, Object> reportData(HttpServletRequest request) {
        String name = request.getParameter("name");

        Map<String, Object> result = new HashMap<String, Object>();

        List<String> report = new ArrayList<String>();

        if (name.equals("httpclient.get")) {
            report.add("httpclient.get_count");
            report.add("httpclient.get_max");
            report.add("httpclient.get_mean");
            report.add("httpclient.get_min");
            report.add("httpclient.get_p50");
            report.add("httpclient.get_p75");
            report.add("httpclient.get_p95");
            report.add("httpclient.get_p98");
            report.add("httpclient.get_p99");
            report.add("httpclient.get_p999");
            report.add("httpclient.get_stddev");
        }

        if (name.equals("monitor")) {
            report.add("jvm.monitor.CommittedVirtualMemory");
            report.add("jvm.monitor.Count");
            report.add("jvm.monitor.DaemonCount");
            report.add("jvm.monitor.FreePhysicalMemory");
            report.add("jvm.monitor.FreeSwapSpace");
            report.add("jvm.monitor.JVMStartTime");
            report.add("jvm.monitor.JVMUpTime");
            report.add("jvm.monitor.PeakCount");
            report.add("jvm.monitor.ProcessCpuTime");
            report.add("jvm.monitor.SharedLoadedClasses");
            report.add("jvm.monitor.SharedUnloadedClasses");
            report.add("jvm.monitor.SystemLoadAverage");
            report.add("jvm.monitor.TotalLoadedClasses");
            report.add("jvm.monitor.TotalPhysicalMemory");
            report.add("jvm.monitor.TotalStartCount");
            report.add("jvm.monitor.TotalSwapSpace");
            report.add("jvm.monitor.TotalUnloadedClasses");
        }

        if (name.equals("thread")) {
           return  null;
        }

        if (name.equals("servlet")) {
            return null;
        }

        for (int i = 0; i < report.size(); i++) {

            Map<String, String> query = new HashMap<String, String>();

            query.put(Constants.APP, request.getParameter(Constants.APP));
            query.put(Constants.IP, request.getParameter(Constants.IP));
            query.put(Constants.METRICS, report.get(i));
            query.put(Constants.STARTTIME, request.getParameter(Constants.STARTTIME));
            query.put(Constants.ENDTIME, request.getParameter(Constants.ENDTIME));

            result.put(report.get(i), storageRepository.findMetric(query));
        }

        return result;
    }

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
