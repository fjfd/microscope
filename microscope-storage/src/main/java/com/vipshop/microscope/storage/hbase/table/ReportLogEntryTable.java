package com.vipshop.microscope.storage.hbase.table;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Hbase schema for 'report_logentry' table.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ReportLogEntryTable {
	
	public static final String TABLE_NAME = "report_logentry";
	
	public static final String CF = "cf";
	
	public static final byte[] BYTE_CF = Bytes.toBytes(CF);

    public static byte[] rowkey() {
        return null;
    }

}
