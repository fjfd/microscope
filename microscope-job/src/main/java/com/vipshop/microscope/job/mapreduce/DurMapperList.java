package com.vipshop.microscope.job.mapreduce;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import org.apache.hadoop.hbase.client.Result;

public class DurMapperList {
	private LinkedList<Result> list;
	private int MaxSize = 1;

	public DurMapperList(int size) {
		this.MaxSize = size;
		this.list = new LinkedList<Result>();
	}

	public void addResult(Result result) {
		if (this.list.size() < this.MaxSize) {
			this.list.add(result);
			sort();
		} else if (ByteUtil.compare(((Result) this.list.getLast()).getValue(TableTrace.F_CFINFO_BYTE, TableTrace.Q_DURATION_BYTE),
				result.getValue(TableTrace.F_CFINFO_BYTE, TableTrace.Q_DURATION_BYTE)) < 0) {
			this.list.removeLast();
			this.list.addLast(result);
			sort();
		}
	}

	private void sort() {
		Collections.sort(this.list, new Comparator<Result>() {
			public int compare(Result o1, Result o2) {
				return ByteUtil.compare(o2.getValue(TableTrace.F_CFINFO_BYTE, TableTrace.Q_DURATION_BYTE), o1.getValue(TableTrace.F_CFINFO_BYTE, TableTrace.Q_DURATION_BYTE));
			}
		});
	}

	public int getSize() {
		return this.list.size();
	}

	public Result getResult(int i) {
		return (Result) this.list.get(i);
	}

	public LinkedList<Result> getList() {
		return this.list;
	}
}