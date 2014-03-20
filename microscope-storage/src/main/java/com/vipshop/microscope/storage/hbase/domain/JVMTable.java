package com.vipshop.microscope.storage.hbase.domain;

import org.apache.hadoop.hbase.util.Bytes;

public class JVMTable {
	// ********** HBase schema for exception index table ******** //
	
	public static final String INDEX_TABLE_NAME = "jvm_index";
	
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
	
	
	// ********* Hbase schema for exception table *********** //
	
	public static final String TABLE_NAME = "jvm";
	
	public static final String CF = "cf";
	public static final String C_METRICS = "metrics";
	
	public static final byte[] BYTE_CF = Bytes.toBytes(CF);
	public static final byte[] BYTE_C_METRICS = Bytes.toBytes(C_METRICS);
}
