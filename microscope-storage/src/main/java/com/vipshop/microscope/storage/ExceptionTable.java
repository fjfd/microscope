package com.vipshop.microscope.storage;

import com.vipshop.microscope.client.exception.ExceptionData;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase schema for store exception data
 */
public class ExceptionTable {

    public static final String TABLE_EXCEPTION_INDEX = "exception_index";

    public static final String CF_APP = "cf_app";
    public static final String CF_IP = "cf_ip";
    public static final String CF_EXCEPTION = "cf_exception";

    public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
    public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
    public static final byte[] BYTE_CF_EXCEPTION = Bytes.toBytes(CF_EXCEPTION);

    public static byte[] rowKeyIndex(ExceptionData exception) {
        return Bytes.toBytes(exception.getApp());
    }

    public static final String TABLE_EXCEPTION = "exception";
    public static final String CF = "c";

    public static final String C_EXCEPTION = "e";
    public static final byte[] BYTE_CF = Bytes.toBytes(CF);

    public static final byte[] BYTE_C_EXCEPTION = Bytes.toBytes(C_EXCEPTION);

    public static String rowKey(ExceptionData exception) {
        return exception.getApp() + "-" +
               exception.getIp() + "-" +
               exception.getExceptionName() + "-" +
               (Long.MAX_VALUE - exception.getDate());
    }

}
