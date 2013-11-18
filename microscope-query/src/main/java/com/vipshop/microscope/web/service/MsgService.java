package com.vipshop.microscope.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vipshop.microscope.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;

public class MsgService {
	
	ReportRepository repository = ReportRepository.getRepository();
	
	public Map<String, Object> getMsgReport(MsgReportCondition condition) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		MsgReport msgReport = repository.findMsgReport(condition);
		Map<String, Long> msgReportResult = new HashMap<String, Long>();
		msgReportResult.put("msg_num", msgReport.getMsgNum());
		msgReportResult.put("msg_size", msgReport.getMsgSize());
		
		List<Map<String, Long>> msgReportsResult = new ArrayList<Map<String,Long>>();
		List<MsgReport> msgReports = repository.findMsgReportTrend(condition);
		for (MsgReport trend : msgReports) {
			Map<String, Long> trendResult = new HashMap<String, Long>();
			trendResult.put("msg_num", trend.getMsgNum());
			trendResult.put("msg_size", trend.getMsgSize());
			msgReportsResult.add(trendResult);
		}
		result.put("msgReport", msgReportResult);
		result.put("msgReportTrend", msgReportsResult);
		
		return result;
	}
}
