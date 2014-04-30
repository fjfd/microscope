package com.vipshop.microscope.storage.mysql.repository;

import com.vipshop.microscope.storage.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.storage.mysql.domain.TraceOverTimeReport;
import com.vipshop.microscope.storage.mysql.domain.TraceReport;

import java.util.List;

public interface TraceReportRepository {

    public long countTraceReport();

    public void saveTraceReport(TraceReport traceReport);

    public List<TraceReport> findTraceReport(TraceReportCondition condition);

    public TraceReport findTraceDuration(TraceReportCondition condition);

    public List<TraceReport> findAppName();

    public List<TraceReport> findIPAdress(String appName);

    public void empty();

    public long countTraceOverTimeReport();

    public void saveOverTimeReport(TraceOverTimeReport overTimeReport);

    public List<TraceOverTimeReport> findOverTimeReport(TraceReportCondition condition);

    public void emptyOverTime();

}
