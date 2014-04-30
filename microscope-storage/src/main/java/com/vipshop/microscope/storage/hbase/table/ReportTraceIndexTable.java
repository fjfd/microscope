package com.vipshop.microscope.storage.hbase.table;

import com.vipshop.microscope.trace.Constants;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 * HBase schema for 'report_trace_index' table
 */
public class ReportTraceIndexTable {

    /**
     * table name
     */
    public static final String TABLE_NAME = "report_trace_index";

    /**
     * column family
     */
    public static final String CF_APP = "cf_app";
    /**
     * column family in byte[] format
     */
    public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
    public static final String CF_IP = "cf_ip";
    public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
    public static final String CF_NAME = "cf_name";
    public static final byte[] BYTE_CF_NAME = Bytes.toBytes(CF_NAME);

    public static byte[] rowKey(Map<String, Object> exception) {
        return Bytes.toBytes((String) exception.get(Constants.APP));
    }

}
