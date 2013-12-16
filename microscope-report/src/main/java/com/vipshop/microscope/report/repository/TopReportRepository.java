package com.vipshop.microscope.report.repository;

import java.util.List;

import com.vipshop.microscope.report.condition.TopReportCondition;
import com.vipshop.microscope.report.domain.TopReport;

public interface TopReportRepository {
	
	public void save(TopReport report);
	
	public long count();
	
	public List<TopReport> find(TopReportCondition condition);
	
	public void empty();
}
