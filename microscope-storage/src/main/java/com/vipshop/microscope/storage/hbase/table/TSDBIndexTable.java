package com.vipshop.microscope.storage.hbase.table;

import com.vipshop.microscope.common.logentry.Constants;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

public class TSDBIndexTable {

    // ********** HBase schema for tsdb index table ******** //

    public static final String TABLE_NAME = "tsdb_index";

    /*
     * column family
     */
    public static final String CF_APP = "cf_app";
    public static final String CF_IP = "cf_ip";
    public static final String CF_METRICS = "cf_metrics";

    /*
     * column family in byte[] fromat
     */
    public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
    public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
    public static final byte[] BYTE_CF_METRICS = Bytes.toBytes(CF_METRICS);

    public static byte[] rowKey(Map<String, Object> exception) {
        return Bytes.toBytes((String)exception.get(Constants.APP));
    }

}
