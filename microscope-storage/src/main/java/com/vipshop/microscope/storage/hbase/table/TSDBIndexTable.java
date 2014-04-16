package com.vipshop.microscope.storage.hbase.table;

import com.vipshop.microscope.common.logentry.Constants;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 * HBase schema for 'tsdb_index' table
 */
public class TSDBIndexTable {

    /**
     * table name
     */
    public static final String TABLE_NAME = "tsdb_index";

    /**
     * column family
     */
    public static final String CF_APP = "cf_app";
    public static final String CF_IP = "cf_ip";
    public static final String CF_METRICS_1 = "cf_metrics_1";
    public static final String CF_METRICS_2 = "cf_metrics_2";
    public static final String CF_METRICS_3 = "cf_metrics_3";
    public static final String CF_METRICS_4 = "cf_metrics_4";

    /**
     * column family in byte[] format
     */
    public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
    public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
    public static final byte[] BYTE_CF_METRICS_1 = Bytes.toBytes(CF_METRICS_1);
    public static final byte[] BYTE_CF_METRICS_2 = Bytes.toBytes(CF_METRICS_2);
    public static final byte[] BYTE_CF_METRICS_3 = Bytes.toBytes(CF_METRICS_3);
    public static final byte[] BYTE_CF_METRICS_4 = Bytes.toBytes(CF_METRICS_4);


}
