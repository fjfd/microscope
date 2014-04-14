package com.vipshop.microscope.storage.hbase.table;

import com.vipshop.microscope.common.system.SystemInfo;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

public class SystemTable {
	
	// ********* Hbase schema for system table *********** //
	
	public static final String TABLE_NAME = "system";
	
	public static final String CF = "cf";
	public static final String C_SYSTEM = "system";
	
	public static final byte[] BYTE_CF = Bytes.toBytes(CF);
	public static final byte[] BYTE_C_SYSTEM = Bytes.toBytes(C_SYSTEM);
	
	public static String rowKey(SystemInfo info) {
		return info.getApp() + info.getHost();
	}

}
