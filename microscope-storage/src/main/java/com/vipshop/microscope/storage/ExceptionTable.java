package com.vipshop.microscope.storage;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 *
 */
public class ExceptionTable {

    // ********* Hbase schema for exception table *********** //

    public static final String TABLE_NAME = "exception";

    public static final String CF = "cf";
    public static final byte[] BYTE_CF = Bytes.toBytes(CF);
    public static final String C_EXCEPTION = "exception";
    public static final byte[] BYTE_C_EXCEPTION = Bytes.toBytes(C_EXCEPTION);

    public static String rowKey(Map<String, Object> map) {
        return map.get("APP") + "-" +
                map.get("IP") + "-" +
                map.get("Name") + "-" +
                (Long.MAX_VALUE - Long.valueOf(map.get("Date").toString()));
    }

}
