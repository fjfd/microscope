package com.vipshop.microscope.common.queue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class FixedPriorityQueue<E> {
	
	private PriorityQueue<E> queue;
	private int maxSize; 
	
	private Comparator<E> comparator;

	public FixedPriorityQueue(int maxSize, Comparator<E> comparator) {
		if (maxSize <= 0)
			throw new IllegalArgumentException();
		this.maxSize = maxSize;
		this.queue = new PriorityQueue<E>(maxSize);
		this.comparator = comparator;
	}

	public void add(E e) {
		if (queue.size() < maxSize) {
			queue.add(e);
		} else { 
			E peek = queue.peek();
			if (comparator.compare(e, peek) < 0) {
				queue.poll();
				queue.add(e);
				
			}
		}
	}
	
	public PriorityQueue<E> getQueue() {
		return queue;
	}

	public static void main(String[] args) {
		Comparator<Integer> comparator = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1.intValue() > o2.intValue()) {
					return -1;
				} else if (o1.intValue() == o2.intValue()) {
					return 0;
				} else {
					return 1;
				}
			}
		};
		
		final FixedPriorityQueue<Integer> pq = new FixedPriorityQueue<Integer>(10, comparator);
		for (int i = 0; i < 100; i++) {
			pq.add(i);
		}
		
		System.out.println(pq.queue.toString());
		
		
	}
}
