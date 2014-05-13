package com.vipshop.microscope.client.transporter;

import com.vipshop.microscope.client.storage.Storages;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.client.Tracer;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.client.storage.Storage;
import com.vipshop.microscope.thrift.ThriftCategory;
import com.vipshop.microscope.thrift.ThriftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Use a {@code Thread} transport message to collector.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ArrayBlockingQueueTransporter implements Transporter {

    private final Logger logger = LoggerFactory.getLogger(ArrayBlockingQueueTransporter.class);

    private final Storage storage = Storages.getStorage();

    ArrayBlockingQueueTransporter(){}

    /**
     * use thrift client send {@code LogEntry}
     */
    private ThriftClient client = new ThriftClient(Tracer.COLLECTOR_HOST,
                                                   Tracer.COLLECTOR_PORT,
                                                   Tracer.RECONNECT_WAIT_TIME,
                                                   ThriftCategory.THREAD_SELECTOR);

//    private ThriftClient client = null;

    private final int MAX_EMPTY_SIZE = Tracer.MAX_EMPTY_SIZE;
    private final int MAX_BATCH_SIZE = Tracer.MAX_BATCH_SIZE;

    /**
     * use for batch send
     */
    private final List<LogEntry> logEntries = new ArrayList<LogEntry>(MAX_BATCH_SIZE);

    private int emptySize = 0;

    private List<String> serverList = new ArrayList<String>(10);

    @Override
    public void transport() {

//        try {
//            zooKeeperClient.connectZookeeper();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if(!serverList.isEmpty()) {
//            logger.info("server list --> " + serverList);
//            String data = serverList.get(0);
//            client = new ThriftClient(data.split(":")[0], Integer.valueOf(data.split(":")[1]), 3000, ThriftCategory.THREAD_SELECTOR);
//        }

        logger.info("start queue transporter thread send LogEntry");

        ExecutorService executor = ThreadPoolUtil.newSingleDaemonThreadExecutor("queue-transporter");

        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    LogEntry logEntry = storage.poll();

                    if (logEntry == null)
                        emptySize++;
                    else {
                        logEntries.add(logEntry);
                    }

                    boolean emptySizeFlag = emptySize >= MAX_EMPTY_SIZE && !logEntries.isEmpty();
                    boolean batchSizeFlag = logEntries.size() >= MAX_BATCH_SIZE;

                    if (emptySizeFlag || batchSizeFlag) {
                        client.send(logEntries);
                        logEntries.clear();
                        emptySize = 0;
                    } else {
                        try {
                            TimeUnit.MICROSECONDS.sleep(Tracer.SEND_WAIT_TIME);
                        } catch (InterruptedException e) {
                            logger.debug("Ignore Thread Interrupted exception", e);
                        }
                    }
                }
            }
        });
    }

}
