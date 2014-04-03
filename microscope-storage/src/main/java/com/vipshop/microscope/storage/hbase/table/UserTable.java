package com.vipshop.microscope.storage.hbase.table;

import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Store user info and user history.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class UserTable {

	// ********* hbase schema for user table ************ //
	
	/*
	 * table name and cloumn family
	 */
	public static final String TABLE_NAME = "user";
	
	public static final String CF_INFO = "cf_info";
	public static final String CF_HISTORY = "cf_history";
	
	public static final byte[] BYTE_CF_INFO = Bytes.toBytes(CF_INFO);
	public static final byte[] BYTE_CF_HISTORY = Bytes.toBytes(CF_HISTORY);

	public static final String C_INFO_USER = "user";
	
	public static final byte[] BYTE_C_INFO_USER = Bytes.toBytes(C_INFO_USER);
	
	public static byte[] rowKey(Map<String, String> user) {
		return Bytes.toBytes(user.get("username"));
	}

}
