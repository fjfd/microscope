package com.vipshop.microscope.storage.hbase.table;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.hadoop.hbase.util.Bytes;

import com.vipshop.microscope.common.trace.Span;

public class TraceTable {
	
	// ********* hbase schema for trace table *********** //
	
	public static final String TABLE_NAME = "trace";
	public static final String CF = "cf";

	public static final byte[] BYTE_CF = Bytes.toBytes(CF);
	
	public static List<Span> doSort(List<Span> spans) { 
		Collections.sort(spans, new Comparator<Span>() {
			@Override
			public int compare(Span o1, Span o2) {
				if(o1.getStartTime() < o2.getStartTime()){
					return -1;
				} else if(o1.getStartTime() > o2.getStartTime()){
					return 1;
				} else {
					return 0;
				}
			}
		});
		return spans;
	}
}
