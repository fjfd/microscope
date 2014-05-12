package com.vipshop.microscope.test.online;

import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.client.Tracer;
import com.vipshop.microscope.client.trace.Category;

import java.util.concurrent.TimeUnit;

/**
 * Test tool online
 *
 * @author Xu Fei
 * @version 1.0
 */
public class OnLineTool {

    public static final String TRACE = "trace";
    public static final String HBASE = "hbase";
    public static final String IP = "ip";
    public static final String IPCahe = "cache";

    public static void main(String[] args) throws Exception {
        String app = System.getProperty("app");
        if (app.equals(TRACE)) {
            trace();
        }
        if (app.equals(HBASE)) {
            hbase();
        }
    }

    public static void trace() throws InterruptedException {
        Tracer.clientSend("example", Category.Method);
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (Exception e) {
            Tracer.setResultCode(e);
        } finally {
            Tracer.clientReceive();
        }
        TimeUnit.SECONDS.sleep(10);
    }

    public static void hbase() {
        StorageRepository.getStorageRepository().initHBaseTable();
    }

}
