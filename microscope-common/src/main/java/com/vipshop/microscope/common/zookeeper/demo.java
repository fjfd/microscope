package com.vipshop.microscope.common.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class demo {
    //会话超时时间，设置为与系统默认时间一致
    private static final int SESSION_TIMEOUT = 30000;

    //创建ZooKeeper实例
    ZooKeeper zk;

    //创建Watcher实例
    Watcher wh = new Watcher() {
        public void process(org.apache.zookeeper.WatchedEvent event) {
            System.out.println(event.toString());
        }
    };

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        demo dm = new demo();
        dm.createZKInstance();
        dm.ZKOperations();
        dm.ZKClose();
    }

    //初始化ZooKeeper实例
    private void createZKInstance() throws IOException {
        zk = new ZooKeeper("localhost:2181", demo.SESSION_TIMEOUT, this.wh);

    }

    private void ZKOperations() throws IOException, InterruptedException, KeeperException {
        System.out.println("\n1.创建ZooKeeper节点(znode：zoo2,数据：myData2，权限：OPEN_ACL_UNSAFE，节点类型：Persistent");
        zk.create("/zoo2", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("\n2.查看是否创建成功：");
        System.out.println(new String(zk.getData("/zoo2", false, null)));

        System.out.println("\n3.修改节点数据");
        zk.setData("/zoo2", "shenlan211314".getBytes(), -1);

        System.out.println("\n4.查看是否修改成功：");
        System.out.println(new String(zk.getData("/zoo2", false, null)));

        System.out.println("\n5.删除节点");
        zk.delete("/zoo2", -1);

        System.out.println("\n6.查看节点是否被删除：");
        System.out.println("节点状态：[" + zk.exists("/zoo2", false) + "]");
    }

    private void ZKClose() throws InterruptedException {
        zk.close();
    }
}