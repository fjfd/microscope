package com.vipshop.microscope.storage;

import com.vipshop.microscope.client.exception.ExceptionData;
import com.vipshop.microscope.client.metric.MetricData;
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
    public void testFind() throws InterruptedException {
        long timestamp = System.currentTimeMillis() - 10 * 60 * 1000 * 60;
        String metric = "jvm_memory.Usage";
        Map<String, String> tags = new HashMap<String, String>();
        tags.put("APP", "trace");
        tags.put("IP", "10.101.3.111");
        long value = 10;

        Aggregator function = Aggregators.MAX;
        boolean rate = true;

        DataPoints[] dataPoints = storageRepository.find(timestamp, System.currentTimeMillis(), metric, tags, function, rate);

        TimeUnit.SECONDS.sleep(1);

//        for(int i = 0; i < dataPoints.length; i++) {
//            SeekableView views = dataPoints[i].iterator();
//            while (views.hasNext()) {
//                DataPoint dataPoint = views.next();
//                System.out.println(dataPoint.timestamp());
//            }
//        }

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

        MetricData metricData = MetricData.named(metric).withTags(tags).withValue(value).withTimestamp(timestamp).build();

        storageRepository.save(metricData);

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

            MetricData metricData = MetricData.named(metric).withTags(tags).withValue(value).withTimestamp(timestamp).build();

            storageRepository.save(metricData);

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

        List<ExceptionData> result = storageRepository.findExceptionData(query);

        System.out.println(result);
    }

}
