package com.vipshop.microscope.storage.hbase.table;

import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

public class ReportIndexTable {
	
	// ********** HBase schema for report index table ******** //
	
	public static final String TABLE_NAME = "report_index";
	
	/*
	 * column family
	 */
	public static final String CF_APP = "cf_app";
	public static final String CF_IP = "cf_ip";
	public static final String CF_REPORT = "cf_report";
	
	/*
	 * column family in byte[] fromat
	 */
	public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
	public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
	public static final byte[] BYTE_CF_REPORT = Bytes.toBytes(CF_REPORT);
	
	public static String rowKey(Map<String, Object> map) {
		return map.get("APP") + "-" +
	           map.get("IP") + "-" +
	           map.get("Report") + "-" +
			   (Long.MAX_VALUE - Long.valueOf(map.get("Date").toString()));
//	           UUID.randomUUID().getLeastSignificantBits();
	}

}
