package com.vipshop.microscope.storage.hbase.table;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Top table.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TopReportTable {
	
	// ********* Hbase schema for top table *********** //
	
	public static final String TABLE_NAME = "report_top10";
	
	public static final String CF = "cf";
	
	public static final byte[] BYTE_CF = Bytes.toBytes(CF);
	
}
