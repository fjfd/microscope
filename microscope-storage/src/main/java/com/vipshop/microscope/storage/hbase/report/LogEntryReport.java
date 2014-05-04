package com.vipshop.microscope.storage.hbase.report;

import com.vipshop.microscope.thrift.LogEntry;

/**
 * LogEntry report Object
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryReport {

    /**
     * Log size
     */
    private long logEntrySize;

    /**
     * Log number
     */
    private long logEntryNumber;

    /**
     * Row key of current report
     */
    private long rowKey;

    /**
     * Column of current report
     */
    private long column;

    public void update(LogEntry logEntry) {
        this.setLogEntrySize(this.getLogEntrySize() + logEntry.getMessage().length());
        this.setLogEntryNumber(this.getLogEntryNumber() + 1);
    }

    public long getLogEntryNumber() {
        return logEntryNumber;
    }

    public void setLogEntryNumber(long logEntryNumber) {
        this.logEntryNumber = logEntryNumber;
    }

    public long getLogEntrySize() {
        return logEntrySize;
    }

    public void setLogEntrySize(long logEntrySize) {
        this.logEntrySize = logEntrySize;
    }

    public long getRowKey() {
        return rowKey;
    }

    public void setRowKey(long rowKey) {
        this.rowKey = rowKey;
    }

    public long getColumn() {
        return column;
    }

    public void setColumn(long column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "LogEntryReport{" +
                "logEntrySize=" + logEntrySize +
                ", logEntryNumber=" + logEntryNumber +
                '}';
    }

}
