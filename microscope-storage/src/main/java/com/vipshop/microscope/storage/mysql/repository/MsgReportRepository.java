package com.vipshop.microscope.storage.mysql.repository;

import com.vipshop.microscope.storage.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.storage.mysql.domain.MsgReport;

import java.util.List;

public interface MsgReportRepository {

    public void empty();

    public void saveMsgReport(MsgReport msgReport);

    public MsgReport findMsgReport(MsgReportCondition condition);

    public List<MsgReport> findMsgReportTrend(MsgReportCondition condition);
}
