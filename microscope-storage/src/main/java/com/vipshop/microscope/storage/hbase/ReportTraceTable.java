package com.vipshop.microscope.storage.hbase;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Hbase schema for 'report_trace' table
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ReportTraceTable {

    public static final String TABLE_NAME = "report_trace";

    public static final String CF = "cf";

    public static final byte[] BYTE_CF = Bytes.toBytes(CF);

}
