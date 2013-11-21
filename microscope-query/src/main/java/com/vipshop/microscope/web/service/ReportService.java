package com.vipshop.microscope.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vipshop.microscope.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.mysql.condition.SourceReportCondition;
import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;

public class ReportService {
	
	ReportRepository repository = ReportRepository.getRepository();
	
	public Map<String, Object> getMsgReport(MsgReportCondition condition) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		MsgReport msgReport = repository.findMsgReport(condition);
		if (msgReport == null) {
			return null;
		}
		Map<String, Long> msgReportResult = new HashMap<String, Long>();
		msgReportResult.put("msg_num", msgReport.getMsgNum());
		msgReportResult.put("msg_size", msgReport.getMsgSize());
		
		List<Map<String, Object>> msgReportsResult = new ArrayList<Map<String,Object>>();
		List<MsgReport> msgReports = repository.findMsgReportTrend(condition);
		for (MsgReport trend : msgReports) {
			Map<String, Object> trendResult = new HashMap<String, Object>();
			trendResult.put("msg_num", trend.getMsgNum());
			trendResult.put("msg_size", trend.getMsgSize());
			trendResult.put("hour", trend.getHour());
			msgReportsResult.add(trendResult);
		}
		result.put("msgReport", msgReportResult);
		result.put("msgReportTrend", msgReportsResult);
		
		return result;
	}
	
	public Map<String, Object> getAppAndIP() {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<TraceReport> traceReports = repository.findAppName();
		for (TraceReport traceReport : traceReports) {
			String app = traceReport.getApp();
			List<TraceReport> ipResults = repository.findIPAdress(app);
			List<String> ipsList = new ArrayList<String>();
			for (TraceReport ipResult : ipResults) {
				ipsList.add(ipResult.getIpAdress());
			}
			result.put(app, ipsList);
		}
		
		return result;
	}

	public List<Map<String, Object>> getTraceReport(TraceReportCondition condition) {
		
		List<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
		
		List<TraceReport> result = repository.findTraceReport(condition);
		
		for (TraceReport traceReport : result) {
			Map<String, Object> trace = new HashMap<String, Object>();
			trace.put("Type", traceReport.getType());
			trace.put("Name", traceReport.getName());
			trace.put("TotalCount", traceReport.getTotalCount());
			trace.put("FailureCount", traceReport.getFailureCount());
			trace.put("FailurePrecent", traceReport.getFailurePrecent());
			trace.put("Min", traceReport.getMin());
			trace.put("Max", traceReport.getMax());
			trace.put("Avg", traceReport.getAvg());
			trace.put("TPS", traceReport.getTps());
			
			report.add(trace);
		}
		
		return report;
	}
	
	public Map<String, Object> getOverTimeReport(TraceReportCondition condition) {
		
		Map<String, Object> result = new HashMap<String, Object>(); 

		Map<Integer, Integer> duration = new LinkedHashMap<Integer, Integer>();
		TraceReport traceReport = repository.findTraceDuration(condition);
		duration.put(0, traceReport.getRegion_0());
		duration.put(1, traceReport.getRegion_1());
		duration.put(2, traceReport.getRegion_2());
		duration.put(4, traceReport.getRegion_3());
		duration.put(8, traceReport.getRegion_4());
		duration.put(16, traceReport.getRegion_5());
		duration.put(32, traceReport.getRegion_6());
		duration.put(64, traceReport.getRegion_7());
		duration.put(128, traceReport.getRegion_8());
		duration.put(256, traceReport.getRegion_9());
		duration.put(512, traceReport.getRegion_10());
		duration.put(1024, traceReport.getRegion_11());
		duration.put(2048, traceReport.getRegion_12());
		duration.put(4096, traceReport.getRegion_13());
		duration.put(8192, traceReport.getRegion_14());
		duration.put(16384, traceReport.getRegion_15());
		duration.put(32768, traceReport.getRegion_16());
		duration.put(65536, 0);
		
		List<OverTimeReport> overTimeReports = repository.findOverTimeReport(condition);
		
		Map<Integer, Float> avgDuration = new LinkedHashMap<Integer, Float>();
		Map<Integer, Integer> hitOverTime = new LinkedHashMap<Integer, Integer>();
		Map<Integer, Integer> faiOverTime = new LinkedHashMap<Integer, Integer>();
		
		for (int i = 1; i < 13; i++) {
			avgDuration.put(i * 5, 0f);
			hitOverTime.put(i * 5, 0);
			faiOverTime.put(i * 5, 0);
		}
		
		for (OverTimeReport overTimeReport : overTimeReports) {
			avgDuration.put(overTimeReport.getMinute() + 5, overTimeReport.getAvgDura());
			hitOverTime.put(overTimeReport.getMinute() + 5, overTimeReport.getHitCount());
			faiOverTime.put(overTimeReport.getMinute() + 5, overTimeReport.getFailCount());
		}
		
		
		result.put("duration", duration);
		result.put("avgDuration", avgDuration);
		result.put("hit", hitOverTime);
		result.put("fail", faiOverTime);
		return result;
	}
	
	public Map<String, Object> getSourceReport(SourceReportCondition condition) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Map<String, Object>> hourResult = new ArrayList<Map<String,Object>>();
		List<SourceReport> hourReport = repository.findSourceReport(condition);
		for (SourceReport sourceReport : hourReport) {
			Map<String, Object> report = new HashMap<String, Object>();
			report.put("app", sourceReport.getApp());
			report.put("hour", sourceReport.getHour());
			report.put("count", sourceReport.getCount());
			hourResult.add(report);
		}
		
		result.put("hourresult", hourResult);
		
		List<Map<String, Object>> distResult = new ArrayList<Map<String,Object>>();
		List<SourceReport> distReport = repository.findSourceReportDist(condition);
		for (SourceReport sourceReport : distReport) {
			Map<String, Object> report = new HashMap<String, Object>();
			report.put("app", sourceReport.getApp());
			report.put("count", sourceReport.getCount());
			distResult.add(report);
		}
		
		result.put("distResult", distResult);
		
		List<Map<String, Object>> detailResult = new ArrayList<Map<String,Object>>();
		List<SourceReport> detailReport = repository.findSourceReportTOP(condition);
		for (SourceReport sourceReport : detailReport) {
			Map<String, Object> report = new HashMap<String, Object>();
			report.put("app", sourceReport.getApp());
			report.put("count", sourceReport.getCount());
			report.put("fail", sourceReport.getFail());
			report.put("failpre", sourceReport.getFailpre());
			report.put("avg_dura", sourceReport.getAvgDura());
			report.put("tps", sourceReport.getTps());
			detailResult.add(report);
		}
		
		result.put("detailResult", detailResult);
		
		return result;
	}

}
