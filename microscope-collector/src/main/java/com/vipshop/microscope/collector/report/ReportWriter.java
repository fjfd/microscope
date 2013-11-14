package com.vipshop.microscope.collector.report;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.report.DurationDistReport;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;

public class ReportWriter implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportWriter.class);
	
	private static final ConcurrentHashMap<Long, MsgReport> msgContainer = ReportContainer.getMsgcontainer();
	private final ConcurrentHashMap<Long, TraceReport> traceContainer = ReportContainer.getTracecontainer();
	private final ConcurrentHashMap<Long, DurationDistReport> duraDistContainer = ReportContainer.getDuradistcontainer();
	private final ConcurrentHashMap<Long, OverTimeReport> overTimeContainer = ReportContainer.getOvertimecontainer();
	
	private final ReportRepository repository = ReportRepository.getRepository();

	@Override
	public void run() {
		while (true) {
			
			CalendarUtil calendarUtil = new CalendarUtil();
			
			long prekeyHour = ReportFrequency.getPreKeyByHour(calendarUtil);
			long preKey5Minute = ReportFrequency.getPreKeyByMinute(calendarUtil);
			
			MsgReport msgReport = msgContainer.get(prekeyHour);
			if (msgReport != null) {
				try {
					repository.save(msgReport);
					logger.info("save msg report to mysql: " + msgReport);
				} catch (Exception e) {
					logger.error("save msg report to msyql error, ignore it");
				} finally {
					msgContainer.remove(prekeyHour);
					logger.info("remove this report from map after save");
				}
			}
			
			TraceReport report = traceContainer.get(prekeyHour);
			if (report != null) {
				try {
					repository.save(report);
					logger.info("save trace report to mysql: " + report);
				} catch (Exception e) {
					logger.error("save trace report to msyql error, ignore it");
				} finally {
					traceContainer.remove(prekeyHour);
					logger.info("remove this report from map after save ");
				}
			}
			
			DurationDistReport distReport = duraDistContainer.get(prekeyHour);
			if (distReport != null) {
				try {
					repository.save(distReport);
					logger.info("save dura dist report to mysql: " + report);
				} catch (Exception e) {
					logger.error("save dura dist report to msyql error, ignore it");
				} finally {
					duraDistContainer.remove(prekeyHour);
					logger.info("remove this report from map after save ");
				}
			}
			
			OverTimeReport overTimeReport = overTimeContainer.get(preKey5Minute);
			if (overTimeReport != null) {
				try {
					repository.save(overTimeReport);
					logger.info("save overtime report to mysql: " + report);
				} catch (Exception e) {
					logger.error("save over time report to msyql error, ignore it");
				} finally {
					overTimeContainer.remove(preKey5Minute);
					logger.info("remove this report from map after save ");
				}
			}
			
			if (msgReport == null && report == null && distReport == null && overTimeReport == null) {
				
				logger.info("there is no report to write currently ... ");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
