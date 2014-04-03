package com.vipshop.microscope.storage.hbase.table;

import org.apache.hadoop.hbase.util.Bytes;

import com.vipshop.microscope.common.trace.Span;

public class TraceIndexTable {
	
	// ******* Hbase schema for Trace index table ******** //
	
	/*
	 * table name
	 */
	public static final String TABLE_NAME = "trace_index";
	
	/*
	 * column family
	 */
	public static final String CF_APP = "cf_app";
	public static final String CF_IP = "cf_ip";
	public static final String CF_TRACE = "cf_trace";
	
	/*
	 * column family in byte[] fromat
	 */
	public static final byte[] BYTE_CF_APP = Bytes.toBytes(CF_APP);
	public static final byte[] BYTE_CF_IP = Bytes.toBytes(CF_IP);
	public static final byte[] BYTE_CF_TRACE = Bytes.toBytes(CF_TRACE);

	private String appName;
	
	private String ipAdress;

	private String traceName;

	public TraceIndexTable(String appName, String ipAdress, String traceName) {
		this.appName = appName;
		this.ipAdress = ipAdress;
		this.traceName = traceName;
	}
	
	/**
	 * Build AppTable by span.
	 * 
	 * @param span
	 * @return
	 */
	public static TraceIndexTable build(Span span) {
		String appName = span.getAppName();
		String ipAdress = span.getAppIp();
		String traceName = span.getSpanName();
		return new TraceIndexTable(appName, ipAdress, traceName);
	}
	
	/**
	 * Return the rowkey of app table.
	 * 
	 * @return rowkey
	 */
	public byte[] rowKey() {
		return Bytes.toBytes(this.appName);
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getIpAdress() {
		return ipAdress;
	}
	
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public String getTraceName() {
		return traceName;
	}

	public void setTraceName(String traceName) {
		this.traceName = traceName;
	}
	
	@Override
	public String toString() {
		return "AppTable [appName=" + appName + ", traceName=" + traceName + ", ipAdress=" + ipAdress + "]";
	}
	
}
