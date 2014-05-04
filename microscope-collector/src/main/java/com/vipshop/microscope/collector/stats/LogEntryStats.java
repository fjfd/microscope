package com.vipshop.microscope.collector.stats;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.storage.hbase.report.LogEntryReport;
import com.vipshop.microscope.thrift.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Stats LogEntry
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryStats {

    private static final Logger logger = LoggerFactory.getLogger(LogEntryReport.class);

    private static final ConcurrentMap<Long, LogEntryReport> logEntryContainer = new ConcurrentHashMap<Long, LogEntryReport>();

    public void stats(LogEntry logEntry) {

        CalendarUtil calendarUtil = new CalendarUtil();

        /**
         * Put current hour report to {@code ConcurrentHashMap}
         */
        long currentKey = this.getKey(calendarUtil);
        LogEntryReport report = logEntryContainer.get(currentKey);
        if (report == null) {
            report = new LogEntryReport();
        }
        report.update(logEntry);
        logEntryContainer.put(currentKey, report);

        /**
         * Write previous hour report to HBase
         */
        Set<Map.Entry<Long, LogEntryReport>> entries = logEntryContainer.entrySet();
        for (Map.Entry<Long, LogEntryReport> entry : entries) {
            long key = entry.getKey();
            /**
             * get previous key
             */
            if (currentKey != key) {
                LogEntryReport prevReport = entry.getValue();
                try {

                    /**
                     * Set row key and column
                     */
                    prevReport.setRowKey(TimeStampUtil.timestampOfCurrentDay(calendarUtil));
                    prevReport.setColumn(key);

                    storeLogEntryReport(prevReport);
                } catch (Exception e) {
                    logger.error("save LogEntryReport to HBase error, ignore ... " + e);
                } finally {
                    logEntryContainer.remove(key);
                }
            }
        }

    }

    public long getKey(CalendarUtil calendar) {
        return TimeStampUtil.timestampOfCurrentHour(calendar);
    }

    public void storeLogEntryReport(LogEntryReport report) {


    }

}
