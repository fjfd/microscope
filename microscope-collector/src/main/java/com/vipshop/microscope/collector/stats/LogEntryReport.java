package com.vipshop.microscope.collector.stats;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LogEntry report
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryReport {

    private static final Logger logger = LoggerFactory.getLogger(LogEntryReport.class);

    private static final ConcurrentHashMap<String, LogEntryReport> logEntryContainer = new ConcurrentHashMap<String, LogEntryReport>();

    private long logEntrySize;
    private long logEntyNumber;

    public void analyze(LogEntry logEntry) {

        CalendarUtil calendarUtil = new CalendarUtil();

        /**
         * Put report to HashMap
         */
        String currentKey = this.getKey(calendarUtil);
        LogEntryReport report = logEntryContainer.get(currentKey);
        if (report == null) {
            report = new LogEntryReport();
        }
        report.update(logEntry);
        logEntryContainer.put(currentKey, report);

        /**
         * Write report to hbase
         */
        Set<Map.Entry<String, LogEntryReport>> entries = logEntryContainer.entrySet();
        for (Map.Entry<String, LogEntryReport> entry : entries) {
            String key = entry.getKey();
            if (!currentKey.equals(currentKey)) {
                LogEntryReport prevReport = entry.getValue();
                try {
                    prevReport.save(calendarUtil, key);
                } catch (Exception e) {
                    logger.error("save LogEntryReport to hbase error, ignore ... " + e);
                } finally {
                    logEntryContainer.remove(key);
                }
            }
        }

    }

    public void update(LogEntry logEntry) {
        this.setLogEntrySize(this.getLogEntrySize() + logEntry.getMessage().length());
        this.setLogEntyNumber(this.getLogEntyNumber() + 1);
    }

    public void save(CalendarUtil calendarUtil, String column) {
        long rowkey = TimeStampUtil.timestampOfCurrentDay(calendarUtil);

    }

    public String getKey(CalendarUtil calendar) {
        return String.valueOf(TimeStampUtil.timestampOfCurrentHour(calendar));
    }

    public long getLogEntyNumber() {
        return logEntyNumber;
    }

    public void setLogEntyNumber(long logEntyNumber) {
        this.logEntyNumber = logEntyNumber;
    }

    public long getLogEntrySize() {
        return logEntrySize;
    }

    public void setLogEntrySize(long logEntrySize) {
        this.logEntrySize = logEntrySize;
    }

}
