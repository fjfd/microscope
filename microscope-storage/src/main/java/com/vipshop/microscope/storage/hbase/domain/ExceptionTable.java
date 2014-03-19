package com.vipshop.microscope.storage.hbase.domain;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Exception table stores exception index and info.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionTable {
	
	// ********** HBase schema for exception index table ******** //
	
	public static final String INDEX_TABLE_NAME = "exception_index";
	
	/*
	 * column family
	 */
	public static final String CF_APP = "cf_app";
	public static final String CF_IP = "cf_ip";
	public static final String CF_NAME = "cf_trace";
	
	/*
	 * column family in byte[] fromat
	 */
	public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
	public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
	public static final byte[] BYTE_CF_NAME = Bytes.toBytes(CF_NAME);
	
	
	// ********* Hbase schema for exception table *********** //
	
	public static final String TABLE_NAME = "exception";
	
	public static final String CF = "cf";
	public static final String C_STACK = "stack";
	
	public static final byte[] BYTE_CF = Bytes.toBytes(CF);
	public static final byte[] BYTE_C_STACK = Bytes.toBytes(C_STACK);
	
}
