package com.vipshop.microscope.storage.mysql.repository;

import com.vipshop.microscope.storage.mysql.domain.MostReport;

import java.util.List;

public interface MostReportRepository {

    public List<MostReport> findMostReport(int type);
}
