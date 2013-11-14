package com.vipshop.microscope.collector.report;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.report.DurationDistReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;
import com.vipshop.microscope.mysql.timeline.WriteReportFrequency;

public class ReportWriter implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportWriter.class);
	
	private final ConcurrentHashMap<Long, TraceReport> traceContainer = ReportContainer.getTracecontainer();
	private final ConcurrentHashMap<Long, DurationDistReport> duraDistContainer = ReportContainer.getDuradistcontainer();
	private final ConcurrentHashMap<Long, OverTimeReport> overTimeContainer = ReportContainer.getOvertimecontainer();
	
	private final ReportRepository repository = ReportRepository.getRepository();

	@Override
	public void run() {
		while (true) {
			
			CalendarUtil calendarUtil = new CalendarUtil();
			
			long prekeyHour = WriteReportFrequency.getPreKeyByHour(calendarUtil);
			long preKey5Minute = WriteReportFrequency.getPreKeyByMinute(calendarUtil);
			
			TraceReport report = traceContainer.get(prekeyHour);
			if (report != null) {
				try {
					repository.save(report);
					logger.info("save trace report to mysql: " + report);
				} catch (Exception e) {
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
					// TODO: handle exception
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
					// TODO: handle exception
				} finally {
					overTimeContainer.remove(preKey5Minute);
					logger.info("remove this report from map after save ");
				}
			}
			
			if (report == null && distReport == null && overTimeReport == null) {
				
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
