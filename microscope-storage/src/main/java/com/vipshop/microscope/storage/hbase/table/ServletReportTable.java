package com.vipshop.microscope.storage.hbase.table;

import org.apache.hadoop.hbase.util.Bytes;

public class ServletReportTable {
	
	// ********** HBase schema for servlet index table ******** //
	
	public static final String INDEX_TABLE_NAME = "servlet_index";
	
	/*
	 * column family
	 */
	public static final String CF_APP = "cf_app";
	public static final String CF_IP = "cf_ip";
	
	/*
	 * column family in byte[] fromat
	 */
	public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
	public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
	
	
	// ********* Hbase schema for servlet table *********** //
	
	public static final String TABLE_NAME = "servlet";
	
	public static final String CF_SERVLET = "cf_servlet";

	public static final String C_ACTIVE_REQUEST = "c_active_request";
	public static final String C_RESPONSE_CODE = "c_response_code";
	public static final String C_REQUEST = "c_request";
	
	public static final byte[] BYTE_CF_SERVLET = Bytes.toBytes(CF_SERVLET);

	public static final byte[] BYTE_C_ACTIVE_REQUEST = Bytes.toBytes(C_ACTIVE_REQUEST);
	public static final byte[] BYTE_C_RESPONSE_CODE = Bytes.toBytes(C_RESPONSE_CODE);
	public static final byte[] BYTE_C_REQUEST = Bytes.toBytes(C_REQUEST);
	
}
