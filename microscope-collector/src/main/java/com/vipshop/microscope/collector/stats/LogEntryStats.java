package com.vipshop.microscope.collector.stats;

import com.vipshop.microscope.common.logentry.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Stats LogEntry
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryStats {

    private static final Logger logger = LoggerFactory.getLogger(LogEntryReport.class);

    private static final ConcurrentHashMap<String, LogEntryReport> logEntryContainer = new ConcurrentHashMap<String, LogEntryReport>();

    public void stats(LogEntry logEntry) {



    }

}
