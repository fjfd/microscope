package com.vipshop.microscope.storage.hbase.table;

import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

public class ServletReportTable {
	
	// ********* Hbase schema for servlet table *********** //
	
	public static final String TABLE_NAME = "report_servlet";
	
	public static final String CF_SERVLET = "cf_servlet";

	public static final String C_ACTIVE_REQUEST = "c_active_request";
	public static final String C_RESPONSE_CODE = "c_response_code";
	public static final String C_REQUEST = "c_request";
	
	public static final byte[] BYTE_CF_SERVLET = Bytes.toBytes(CF_SERVLET);

	public static final byte[] BYTE_C_ACTIVE_REQUEST = Bytes.toBytes(C_ACTIVE_REQUEST);
	public static final byte[] BYTE_C_RESPONSE_CODE = Bytes.toBytes(C_RESPONSE_CODE);
	public static final byte[] BYTE_C_REQUEST = Bytes.toBytes(C_REQUEST);
	
	public static String rowKey(Map<String, Object> map) {
		return map.get("app") + "-" +
	           map.get("ip") + "-" +
			   (Long.MAX_VALUE - Long.valueOf(map.get("date").toString()));
//	           UUID.randomUUID().getLeastSignificantBits();
	}

}
