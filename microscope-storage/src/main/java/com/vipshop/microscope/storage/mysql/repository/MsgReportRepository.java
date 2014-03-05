package com.vipshop.microscope.storage.mysql.repository;

import java.util.List;

import com.vipshop.microscope.storage.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.storage.mysql.domain.MsgReport;

public interface MsgReportRepository {
	
	public void empty();
	
	public void saveMsgReport(MsgReport msgReport);
	
	public MsgReport findMsgReport(MsgReportCondition condition);

	public List<MsgReport> findMsgReportTrend(MsgReportCondition condition);
}
