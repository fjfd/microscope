package com.vipshop.microscope.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

public class AppClient {

    private Stat stat = new Stat();
    private String groupNode = "sgroup";

    private ZooKeeper zk;
    private volatile List<String> serverList;

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
        List<String> newServerList = new ArrayList<String>();

        List<String> subList = zk.getChildren("/" + groupNode, true);
        for (String subNode : subList) {
            byte[] data = zk.getData("/" + groupNode + "/" + subNode, false, stat);
            newServerList.add(new String(data, "utf-8"));
        }

        serverList = newServerList;

        System.out.println("server list updated: " + serverList);
    }

    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        AppClient ac = new AppClient();
        ac.connectZookeeper();

        ac.handle();
    }
}