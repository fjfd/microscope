package com.vipshop.microscope.storage.hbase.table;

import com.vipshop.microscope.common.logentry.Constants;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 * HBase schema for home index table
 *
 */
public class HomeIndexTable {

    /**
     * table name
     */
	public static final String TABLE_NAME = "home_index";
	
	/**
	 * column family
	 */
	public static final String CF_APP = "cf_app";
	public static final String CF_IP = "cf_ip";

	/**
	 * column family in byte[] format
	 */
	public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
	public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);

	public static byte[] rowKey(Map<String, Object> exception) {
		return Bytes.toBytes((String)exception.get(Constants.APP));
	}

}
