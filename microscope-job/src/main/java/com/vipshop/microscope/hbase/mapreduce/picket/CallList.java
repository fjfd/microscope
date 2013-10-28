package com.vipshop.microscope.hbase.mapreduce.picket;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class CallList {

	LinkedList<PicketGuy> list = new LinkedList<PicketGuy>();
	private HashMap<String, Integer> map;

	public CallList() {
		map = new HashMap<String, Integer>();
	}

	public void add(String tname) {
		if (null != map.get(tname)) {
			int tmp = map.get(tname);
			map.put(tname, (tmp + 1));
		} else {
			map.put(tname, 1);
		}
	}

	public LinkedList<PicketGuy> getList() {
		for (String key : map.keySet()) {
			list.add(new PicketGuy(key, map.get(key)));
		}
		sort();

		return list;
	}

	private void sort() {
		Collections.sort(list, new Comparator<PicketGuy>() {
			@Override
			public int compare(PicketGuy o1, PicketGuy o2) {
				return o2.getPosition() - o1.getPosition();
			}
		});
	}
}
