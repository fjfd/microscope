package com.vipshop.microscope.zookeeper;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * ZooKeeper simple client
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ZooKeeperClient {

    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperClient.class);

    public static final int SESSION_TIMEOUT = 30000;

    ZooKeeper zk;

    Watcher wh = new Watcher() {
        public void process(WatchedEvent event) {
            // TODO
        }
    };

    public ZooKeeperClient() throws IOException {
        zk = new ZooKeeper("10.101.3.79:2181", SESSION_TIMEOUT, this.wh);
    }

    public ZooKeeper getZooKeeper() {
        return zk;
    }

    public void create(String groupPath, String data) throws KeeperException, InterruptedException {
        String cGroupPath = zk.create(groupPath, data.getBytes(),
                                      ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                      CreateMode.PERSISTENT);
        logger.info("Create a node with the given path --> " + cGroupPath);
    }

    public void delete(String groupPath) throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren(groupPath, false);
        if (!children.isEmpty()) {
            for (String child : children)
                zk.delete(groupPath + "/" + child, -1);
        }
        zk.delete(groupPath, -1);
    }

    public int join(String groupPath, int k) throws KeeperException, InterruptedException {
        String child = k + "";
        child = "child_" + child;

        String path = groupPath + "/" + child;
        if (zk.exists(groupPath, true) != null) {
            zk.create(path, child.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return 1;
        } else {
            logger.error("group --> " + groupPath + " does not exist");
            return 0;
        }
    }

    public List<String> list(String groupPath) throws KeeperException, InterruptedException {
        return zk.getChildren(groupPath, false);
    }

    public void close() throws InterruptedException {
        zk.close();
    }
}
