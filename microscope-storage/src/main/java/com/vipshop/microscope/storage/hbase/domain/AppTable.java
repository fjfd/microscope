package com.vipshop.microscope.storage.hbase.domain;

import org.apache.hadoop.hbase.util.Bytes;

import com.vipshop.microscope.common.trace.Span;

/**
 * AppTable store app name, ip adress, trace name.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class AppTable {

	private String appName;
	
	private String ipAdress;

	private String traceName;

	public AppTable(String appName, String ipAdress, String traceName) {
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
	public static AppTable build(Span span) {
		String appName = span.getAppName();
		String ipAdress = span.getAppIp();
		String traceName = span.getSpanName();
		return new AppTable(appName, ipAdress, traceName);
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
