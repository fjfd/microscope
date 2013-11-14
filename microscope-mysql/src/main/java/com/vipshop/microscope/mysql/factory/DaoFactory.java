package com.vipshop.microscope.mysql.factory;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vipshop.microscope.mysql.dao.MsgReportDao;
import com.vipshop.microscope.mysql.dao.TraceReportDao;

public class DaoFactory {
	
	public static final TraceReportDao TRACE_REPORT_DAO;
	public static final MsgReportDao MSG_REPORT_DAO;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-database.xml", DaoFactory.class);
		
		TRACE_REPORT_DAO = context.getBean(TraceReportDao.class);
		MSG_REPORT_DAO = context.getBean(MsgReportDao.class);
		
		context.close();
	}
}
