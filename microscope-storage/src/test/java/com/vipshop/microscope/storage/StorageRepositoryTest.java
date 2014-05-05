package com.vipshop.microscope.storage;

import net.opentsdb.core.*;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StorageRepositoryTest {

    StorageRepository storageRepository = StorageRepository.getStorageRepository();

    @Test
    public void initHbaseTable() {
        storageRepository.initHBaseTable();
    }

    @Test
    public void dropTable() {
        storageRepository.dropHBaseTable();
    }

    @Test
    public void createTable() {
        storageRepository.createHBaseTable();
    }

    @Test
    public void testSuggestMetrics() {
        System.out.println(storageRepository.suggestMetrics("jvm"));
    }

    @Test
    public void testFind() throws InterruptedException {
        long timestamp = System.currentTimeMillis();
        String metric = "jvm_memory.Usage";
        Map<String, String> tags = new HashMap<String, String>();
        tags.put("APP", "trace");
        tags.put("IP", "10.101.3.111");
        long value = 10;

        Aggregator function = Aggregators.MAX;
        boolean rate = true;

        DataPoints[] dataPoints = storageRepository.find(timestamp - 10 * 60 * 1000 * 60, timestamp, metric, tags, function, rate);
        TimeUnit.SECONDS.sleep(1);
        for (int i = 0; i < dataPoints.length; i++) {
            System.out.println(dataPoints[i].toString());
        }
    }

    @Test
    public void testAddPoint() throws InterruptedException {
        long timestamp = System.currentTimeMillis();
        String metric = "jvm_memory.Usage";
        Map<String, String> tags = new HashMap<String, String>();
        tags.put("APP", "trace");
        tags.put("IP", "10.101.3.111");
        long value = 10;

        storageRepository.save(metric, timestamp, value, tags);

        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void testAddPointLoop() throws InterruptedException {
        while (true) {
            long timestamp = System.currentTimeMillis();
            String metric = "jvm_memory.Committed";
            Map<String, String> tags = new HashMap<String, String>();
            tags.put("APP", "trace");
            tags.put("IP", "10.101.3.111");
            long value = 10;

            storageRepository.save(metric, timestamp, value, tags);

            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testFindExceptionList() {
        Map<String, String> query = new HashMap<String, String>();

        query.put("appName", "trace");
        query.put("ipAddress", "10.101.3.111");

        query.put("limit", "100");
        query.put("startTime", String.valueOf(System.currentTimeMillis() - 1000 * 60 * 60));
        query.put("endTime", String.valueOf(System.currentTimeMillis()));

        List<Map<String, Object>> result = storageRepository.findExceptionList(query);

        System.out.println(result);
    }

    @Test
    public void testFindTop() {
        System.out.println(storageRepository.findTopList());
    }

}
