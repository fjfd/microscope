package com.vipshop.microscope.common.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

public class ListMembers extends ZooKeeperInstance {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ListMembers lm = new ListMembers();
        lm.createZKInstance();
        lm.list("/ZKGroup");
    }

    public void list(String groupPath) throws KeeperException, InterruptedException {
        //获取所有子节点
        List<String> children = zk.getChildren(groupPath, false);
        if (children.isEmpty()) {
            System.out.println("组" + groupPath + "中没有组成员存在！");
            System.exit(1);
        }
        for (String child : children)
            System.out.println(child);
    }
}
