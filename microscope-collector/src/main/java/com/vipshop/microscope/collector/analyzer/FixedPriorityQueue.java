package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.thrift.Span;

import java.util.Comparator;
import java.util.TreeMap;

public class FixedPriorityQueue {

    private int initialCapacity;

    /**
     * A sorted map store top 10 data<K,V>
     * <p/>
     * key   --> span duration
     * value --> app name
     */
    private TreeMap<Integer, Span> container = new TreeMap<Integer, Span>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1.intValue() > o2.intValue()) {
                return -1;
            }
            if (o1.intValue() < o2.intValue()) {
                return 1;
            }
            return 0;
        }
    });

    public FixedPriorityQueue(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public void add(Span span) {
        container.put(span.getDuration(), span);

        if (container.size() > this.initialCapacity) {
            container.pollLastEntry();
        }
    }

    public TreeMap<Integer, Span> getQueue() {
        return container;
    }

    @Override
    public String toString() {
        return container.toString();
    }

}
