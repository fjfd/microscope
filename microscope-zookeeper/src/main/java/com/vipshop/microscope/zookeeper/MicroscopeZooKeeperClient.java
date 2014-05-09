package com.vipshop.microscope.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

public class MicroscopeZooKeeperClient {

    private Stat stat = new Stat();
    private String groupNode = "sgroup";

    private ZooKeeper zk;
    private volatile List<String> serverList;

    public MicroscopeZooKeeperClient(List<String> data) {
        this.serverList = data;
    }

    public void connectZookeeper() throws Exception {
        zk = new ZooKeeper("10.101.3.79:2181", 5000, new Watcher() {
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.NodeChildrenChanged
                    && ("/" + groupNode).equals(event.getPath())) {
                    try {
                        updateServerList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        updateServerList();
    }

    private void updateServerList() throws Exception {
        serverList.clear();
        List<String> subList = zk.getChildren("/" + groupNode, true);
        for (String subNode : subList) {
            byte[] data = zk.getData("/" + groupNode + "/" + subNode, false, stat);
            serverList.add(new String(data, "utf-8"));
        }

//        System.out.println("server list --> " + serverList);
    }

    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        MicroscopeZooKeeperClient ac = new MicroscopeZooKeeperClient(new ArrayList<String>());
        ac.connectZookeeper();

        ac.handle();
    }
}