package com.vipshop.microscope.storage.hbase.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vipshop.microscope.common.trace.Span;

/**
 * SpanTable store span.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class SpanTable {
	
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
