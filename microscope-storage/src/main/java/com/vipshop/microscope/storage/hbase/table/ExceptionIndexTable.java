package com.vipshop.microscope.storage.hbase.table;

import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

public class ExceptionIndexTable {
	
	// ********** HBase schema for exception index table ******** //
	
	public static final String TABLE_NAME = "exception_index";
	
	/*
	 * column family
	 */
	public static final String CF_APP = "cf_app";
	public static final String CF_IP = "cf_ip";
	public static final String CF_NAME = "cf_exception";
	
	/*
	 * column family in byte[] fromat
	 */
	public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
	public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
	public static final byte[] BYTE_CF_NAME = Bytes.toBytes(CF_NAME);
	
	public static String rowKey(Map<String, Object> map) {
		return map.get("APP") + "-" +
	           map.get("IP") + "-" +
	           map.get("Name") + "-" +
			   (Long.MAX_VALUE - Long.valueOf(map.get("Date").toString()));
//	           UUID.randomUUID().getLeastSignificantBits();
	}

}
