package com.vipshop.microscope.report.repository;

import java.util.List;

import com.vipshop.microscope.report.condition.MsgReportCondition;
import com.vipshop.microscope.report.domain.MsgReport;

public interface MsgReportRepository {
	
	public void empty();
	
	public void saveMsgReport(MsgReport msgReport);
	
	public MsgReport findMsgReport(MsgReportCondition condition);

	public List<MsgReport> findMsgReportTrend(MsgReportCondition condition);
}
