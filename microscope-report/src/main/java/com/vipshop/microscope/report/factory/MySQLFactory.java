package com.vipshop.microscope.report.factory;

import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.report.repository.DepenReportRepository;
import com.vipshop.microscope.report.repository.MarketReportRepository;
import com.vipshop.microscope.report.repository.MostReportRepository;
import com.vipshop.microscope.report.repository.MsgReportRepository;
import com.vipshop.microscope.report.repository.ProblemReportRepository;
import com.vipshop.microscope.report.repository.SourceReportRepository;
import com.vipshop.microscope.report.repository.TopReportRepository;
import com.vipshop.microscope.report.repository.TraceReportRepository;

public class MySQLFactory {
	
	private static DataSource dataSource;
	
	public static final MarketReportRepository MARKET;
	public static final TopReportRepository TOP;
	public static final MostReportRepository MOST;
	public static final ProblemReportRepository PROBLEM;
	public static final TraceReportRepository TRACE;
	public static final SourceReportRepository SOURCE;
	public static final DepenReportRepository DEPEN;
	public static final MsgReportRepository MSG;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-query-database.xml", MySQLFactory.class);
		MARKET = context.getBean(MarketReportRepository.class);
		TOP = context.getBean(TopReportRepository.class);
		MOST = context.getBean(MostReportRepository.class);
		PROBLEM = context.getBean(ProblemReportRepository.class);
		TRACE = context.getBean(TraceReportRepository.class);
		SOURCE = context.getBean(SourceReportRepository.class);
		DEPEN = context.getBean(DepenReportRepository.class);
		MSG = context.getBean(MsgReportRepository.class);
		
		dataSource = context.getBean(com.mchange.v2.c3p0.ComboPooledDataSource.class);
		context.close();
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
}
