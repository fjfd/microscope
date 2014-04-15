package com.vipshop.microscope.storage.hbase.table;

import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Hbase schema for 'user' table.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class UserTable {

	/**
	 * table name
	 */
	public static final String TABLE_NAME = "user";

    /**
     * column family
     */
	public static final String CF_INFO = "cf_info";
	public static final String CF_HISTORY = "cf_history";

    /**
     * column family byte
     */
	public static final byte[] BYTE_CF_INFO = Bytes.toBytes(CF_INFO);
	public static final byte[] BYTE_CF_HISTORY = Bytes.toBytes(CF_HISTORY);

	public static final String C_INFO_USER = "user";
	
	public static final byte[] BYTE_C_INFO_USER = Bytes.toBytes(C_INFO_USER);
	
	public static byte[] rowKey(Map<String, String> user) {
		return Bytes.toBytes(user.get("username"));
	}

}
