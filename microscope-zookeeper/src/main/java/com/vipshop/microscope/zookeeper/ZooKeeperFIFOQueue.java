package com.vipshop.microscope.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Distributed FIFO Queue use zookeeper
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ZooKeeperFIFOQueue extends ConnectionWatcher {

    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperFIFOQueue.class);

    public void initQueue() throws KeeperException, InterruptedException {
        if (zk.exists("/queue-fifo", false) == null) {
            logger.info("create /queue-fifo task-queue-fifo");
            zk.create("/queue-fifo", "task-queue-fifo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            logger.info("/queue-fifo is exist!");
        }
    }

    public void produce(int x) throws KeeperException, InterruptedException {
        logger.info("create /queue-fifo/x" + x + " x" + x);
        zk.create("/queue-fifo/x" + x, ("x" + x).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public void cosume() throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren("/queue-fifo", true);
        if (list.size() > 0) {
            long min = Long.MAX_VALUE;
            for (String num : list) {
                if (min > Long.parseLong(num.substring(1))) {
                    min = Long.parseLong(num.substring(1));
                }
            }
            logger.info("delete /queue/x" + min);
            zk.delete("/queue-fifo/x" + min, 0);
        } else {
            logger.info("No node to cosume");
        }
    }

    public static void main(String[] args) throws Exception {
        ZooKeeperFIFOQueue queue = new ZooKeeperFIFOQueue();

        queue.connect("10.101.3.91:2181");

        queue.initQueue();
        queue.produce(1);
        queue.produce(2);
        queue.cosume();
        queue.cosume();
        queue.cosume();
    }


}
