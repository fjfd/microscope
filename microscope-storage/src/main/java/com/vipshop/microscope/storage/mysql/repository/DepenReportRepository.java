package com.vipshop.microscope.storage.mysql.repository;

import com.vipshop.microscope.storage.mysql.domain.DepenReport;

public interface DepenReportRepository {

    public void empty();

    public void save(DepenReport report);
}
