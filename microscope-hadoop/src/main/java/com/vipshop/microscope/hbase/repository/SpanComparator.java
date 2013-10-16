package com.vipshop.microscope.hbase.repository;

import java.util.Comparator;

import com.vipshop.microscope.thrift.Span;

public class SpanComparator implements Comparator<Span> {

	@Override
	public int compare(Span o1, Span o2) {
		if(o1.getOrder() < o2.getOrder()){
			return -1;
		}
		if(o1.getOrder() > o2.getOrder()){
			return 1;
		}
		return 0;
	}
}