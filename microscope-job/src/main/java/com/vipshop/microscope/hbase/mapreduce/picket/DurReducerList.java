package com.vipshop.microscope.hbase.mapreduce.picket;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class DurReducerList {
	private LinkedList<PicketGuy> list;
	private int MaxSize = 1;

	public DurReducerList(int size) {
		this.MaxSize = size;
		this.list = new LinkedList<PicketGuy>();
	}

	public void add(PicketGuy guy) {
		if (this.list.size() < this.MaxSize) {
			this.list.add(guy);
			sort();
		} else if (((PicketGuy) this.list.getLast()).getPosition() < guy.getPosition()) {
			this.list.removeLast();
			this.list.addLast(guy);
			sort();
		}
	}

	private void sort() {
		Collections.sort(this.list, new Comparator<PicketGuy>() {
			public int compare(PicketGuy o1, PicketGuy o2) {
				return o2.getPosition() - o1.getPosition();
			}
		});
	}

	public LinkedList<PicketGuy> getList() {
		return this.list;
	}
}