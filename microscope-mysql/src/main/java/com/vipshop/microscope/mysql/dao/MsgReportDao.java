package com.vipshop.microscope.mysql.dao;

import java.util.List;

import com.vipshop.microscope.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.mysql.report.MsgReport;

public interface MsgReportDao {
	
	public void saveMsgReport(MsgReport msgReport);
	
	public List<MsgReport> findMsgReport(MsgReportCondition condition);
}
