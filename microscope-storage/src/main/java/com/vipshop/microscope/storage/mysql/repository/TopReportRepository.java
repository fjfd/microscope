package com.vipshop.microscope.storage.mysql.repository;

import com.vipshop.microscope.storage.mysql.domain.TopReport;

import java.util.List;

public interface TopReportRepository {

    public void save(TopReport report);

    public long count();

    public List<TopReport> find(int topType);

    public void empty();

}
