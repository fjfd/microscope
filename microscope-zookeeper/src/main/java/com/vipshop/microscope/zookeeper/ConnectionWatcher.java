package com.vipshop.microscope.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * a helper class connect zookeeper.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ConnectionWatcher implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionWatcher.class);

    private CountDownLatch signal = new CountDownLatch(1);

    private static final int SESSION_TIMEOUT = 30000;

    private ZooKeeper zk;

    public void connect(String hosts) throws IOException, InterruptedException {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        signal.await();
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected){
            signal.countDown();
        }
    }

    public void close() throws InterruptedException {
        zk.close();
    }

}
