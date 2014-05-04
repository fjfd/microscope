package com.vipshop.microscope.storage.hbase;

public class TSDBUIDTable {

    public static final String TABLE_NAME = "tsdb_uid";

    public static final String CF_NAME = "name";
    public static final String CF_ID = "id";

    public static byte[] rowKey() {
        return null;
    }

}
