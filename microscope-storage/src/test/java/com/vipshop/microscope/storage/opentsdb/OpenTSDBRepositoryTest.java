package com.vipshop.microscope.storage.opentsdb;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OpenTSDBRepositoryTest {

    private OpenTSDBRepository repository = new OpenTSDBRepository();

    @Test
    public void testAdd() throws InterruptedException {
        String metric = "mysql.http.request";

        long timestamp = System.currentTimeMillis();

        long value = 10;

        Map<String, String> tags = new HashMap<String, String>();

        tags.put("host", "localhost");
        tags.put("app", "test");

        repository.add(metric, timestamp, value, tags);
        TimeUnit.SECONDS.sleep(10);
    }
}
