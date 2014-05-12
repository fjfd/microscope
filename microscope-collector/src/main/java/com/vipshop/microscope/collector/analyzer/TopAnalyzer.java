package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.client.trace.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class TopAnalyzer {

    public static final Logger logger = LoggerFactory.getLogger(TopReport.class);

    private ConcurrentHashMap<Category, FixedPriorityQueue> container;

    public TopAnalyzer() {
        this.container = new ConcurrentHashMap<Category, FixedPriorityQueue>(Category.values().length);
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            container.put(categories[i], new FixedPriorityQueue(10));
        }
    }

    /**
     * Analyze top by span.
     *
     * @param span
     */
    public void analyze(Span span, StorageRepository storager) {
        FixedPriorityQueue queue = container.get(Category.valueOf(span.getSpanType()));
        queue.add(span);
        writeReport(storager);
    }

    public ConcurrentHashMap<Category, FixedPriorityQueue> getContainer() {
        return container;
    }

    private void writeReport(StorageRepository storager) {
        HashMap<String, Object> top = new HashMap<String, Object>();
        for (Entry<Category, FixedPriorityQueue> entry : container.entrySet()) {
            StringBuilder builder = new StringBuilder();
            TreeMap<Integer, Span> queue = entry.getValue().getQueue();
            for (Entry<Integer, Span> treeEntry : queue.entrySet()) {
                Span span = treeEntry.getValue();
                builder.append(span.getAppName())
                        .append("=")
                        .append(span.getTraceId())
                        .append("=")
                        .append(span.getDuration())
                        .append("=")
                        .append(span.getStartTime())
                        .append(";");
            }
            top.put(entry.getKey().getStrValue(), builder.toString());
        }
//        storager.saveAppLog(top);
    }

    static class FixedPriorityQueue {

        private int initialCapacity;

        /**
         * A sorted map saveAppLog top 10 data<K,V>
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
}
