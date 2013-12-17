package com.vipshop.microscope.report.repository;

import java.util.List;

import com.vipshop.microscope.report.condition.MostReportCondition;
import com.vipshop.microscope.report.domain.MostReport;

public interface MostReportRepository {
	
	public List<MostReport> findMostReport(MostReportCondition condition);
}
