package com.vipshop.microscope.hbase.mapreduce.picket;

import org.apache.hadoop.hbase.util.Bytes;

public class TableTrace {
	public static final byte[] F_CFINFO_BYTE = Bytes.toBytes("cf");

	public static final byte[] Q_DURATION_BYTE = Bytes.toBytes("duration");
	public static final byte[] Q_TRACENAME_BYTE = Bytes.toBytes("trace_name");
	public static final String RANGE = "picket_range";
	public static final String START_DATE = "startdate";
	public static final String TOP_SIZE = "picket_top_size";
	public static final String TABLE_TRACE = "picket_top";
}