package com.vipshop.microscope.storage.mysql.repository;

import java.util.List;

import com.vipshop.microscope.storage.mysql.domain.TopReport;

public interface TopReportRepository {
	
	public void save(TopReport report);
	
	public long count();
	
	public List<TopReport> find(int topType);
	
	public void empty();
	
}
