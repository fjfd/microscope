package com.vipshop.microscope.storage.hbase.table;

import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

import com.vipshop.microscope.common.logentry.Constants;

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
	
	public static byte[] rowKey(Map<String, Object> report) {
		return Bytes.toBytes((String)report.get(Constants.APP));
	}


}
