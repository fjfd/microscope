package com.vipshop.microscope.zookeeper;

import org.apache.zookeeper.*;

public class MicroscopeZooKeeperServer {

    private String groupNode = "sgroup";
    private String subNode = "sub";

    public void connectZookeeper(String address) throws Exception {

        ZooKeeper zk = new ZooKeeper("10.101.3.79:2181", 5000, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                // TODO
            }

        });

        String createdPath = zk.create("/" + groupNode + "/" + subNode,
                                       address.getBytes("utf-8"),
                                       ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                       CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println("create: " + createdPath);
    }

    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        MicroscopeZooKeeperServer as = new MicroscopeZooKeeperServer();
        as.connectZookeeper("server-1");
        as.handle();
    }
}