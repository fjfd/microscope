package com.vipshop.microscope.storage;

import com.vipshop.microscope.trace.system.SystemData;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Hbase schema for system table
 */
public class SystemTable {

    public static final String TABLE_NAME = "system";

    public static final String CF = "c";
    public static final String C_SYSTEM = "system";

    public static final byte[] BYTE_CF = Bytes.toBytes(CF);
    public static final byte[] BYTE_C_SYSTEM = Bytes.toBytes(C_SYSTEM);

    public static String rowKey(SystemData system) {
        return system.getApp() + system.getHost();
    }

}
