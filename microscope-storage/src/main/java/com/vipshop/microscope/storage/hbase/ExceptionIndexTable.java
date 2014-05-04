package com.vipshop.microscope.storage.hbase;

import com.vipshop.microscope.trace.Constants;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 * HBase schema for exception index table
 */
public class ExceptionIndexTable {

    /**
     * table name
     */
    public static final String TABLE_NAME = "exception_index";

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
    public static final String CF_EXCEPTION = "cf_exception";
    public static final byte[] BYTE_CF_EXCEPTION = Bytes.toBytes(CF_EXCEPTION);

    /**
     * Create row key of 'exception_index' table.
     *
     * @param exception
     * @return
     */
    public static byte[] rowKey(Map<String, Object> exception) {
        return Bytes.toBytes((String) exception.get(Constants.APP));
    }

}
