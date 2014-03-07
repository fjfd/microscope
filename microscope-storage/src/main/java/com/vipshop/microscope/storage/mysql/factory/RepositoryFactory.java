package com.vipshop.microscope.storage.mysql.factory;

import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.storage.mysql.repository.DepenReportRepository;
import com.vipshop.microscope.storage.mysql.repository.MarketReportRepository;
import com.vipshop.microscope.storage.mysql.repository.MostReportRepository;
import com.vipshop.microscope.storage.mysql.repository.MsgReportRepository;
import com.vipshop.microscope.storage.mysql.repository.ProblemReportRepository;
import com.vipshop.microscope.storage.mysql.repository.SourceReportRepository;
import com.vipshop.microscope.storage.mysql.repository.TopReportRepository;
import com.vipshop.microscope.storage.mysql.repository.TraceReportRepository;

public class RepositoryFactory {
	
	private static final DataSource dataSource;
	
	private static final MarketReportRepository  MARKET_REPORT;
	private static final TopReportRepository     TOP_REPORT;
	private static final MostReportRepository    MOST_REPORT;
	private static final ProblemReportRepository PROBLEM_REPORT;
	private static final TraceReportRepository   TRACE_REPORT;
	private static final SourceReportRepository  SOURCE_REPORT;
	private static final DepenReportRepository   DEPEN_REPORT;
	private static final MsgReportRepository     MSG_REPORT;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-storage-mysql.xml", RepositoryFactory.class);
		
		dataSource = context.getBean(com.mchange.v2.c3p0.ComboPooledDataSource.class);

		MARKET_REPORT = context.getBean(MarketReportRepository.class);
		TOP_REPORT = context.getBean(TopReportRepository.class);
		MOST_REPORT = context.getBean(MostReportRepository.class);
		PROBLEM_REPORT = context.getBean(ProblemReportRepository.class);
		TRACE_REPORT = context.getBean(TraceReportRepository.class);
		SOURCE_REPORT = context.getBean(SourceReportRepository.class);
		DEPEN_REPORT = context.getBean(DepenReportRepository.class);
		MSG_REPORT = context.getBean(MsgReportRepository.class);
		
		context.close();
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}

	public static MarketReportRepository getMarketReportRepository() {
		return MARKET_REPORT;
	}

	public static TopReportRepository getTopReportRepository() {
		return TOP_REPORT;
	}

	public static MostReportRepository getMostReportRepository() {
		return MOST_REPORT;
	}

	public static ProblemReportRepository getProblemReportRepository() {
		return PROBLEM_REPORT;
	}

	public static TraceReportRepository getTraceReportRepository() {
		return TRACE_REPORT;
	}

	public static SourceReportRepository getSourceReportRepository() {
		return SOURCE_REPORT;
	}

	public static DepenReportRepository getDepenReportRepository() {
		return DEPEN_REPORT;
	}

	public static MsgReportRepository getMsgReportRepository() {
		return MSG_REPORT;
	}
}
