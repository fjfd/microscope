package com.vipshop.microscope.storage.hbase.domain;

import org.apache.hadoop.hbase.util.Bytes;

public class JVMTable {
	
	// ********** HBase schema for jvm index table ******** //
	
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
	
	
	// ********* Hbase schema for jvm table *********** //
	
	public static final String TABLE_NAME = "jvm";
	
	public static final String CF_JVM = "cf_jvm";

	public static final String C_OVERVIEW = "c_overview";
	public static final String C_MONITOR = "c_monitor";
	public static final String C_RUNTIME = "c_runtime";
	public static final String C_THREAD = "c_thead";
	public static final String C_MEMORY = "c_memory";
	public static final String C_GC = "c_gc";
	
	public static final byte[] BYTE_CF_JVM = Bytes.toBytes(CF_JVM);

	public static final byte[] BYTE_C_OVERVIEW = Bytes.toBytes(C_OVERVIEW);
	public static final byte[] BYTE_C_MONITOR = Bytes.toBytes(C_MONITOR);
	public static final byte[] BYTE_C_RUNTIME = Bytes.toBytes(C_RUNTIME);
	public static final byte[] BYTE_C_THREAD = Bytes.toBytes(C_THREAD);
	public static final byte[] BYTE_C_GC = Bytes.toBytes(C_GC);
	public static final byte[] BYTE_C_MEMORY = Bytes.toBytes(C_MEMORY);
	
}
