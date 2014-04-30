package com.vipshop.microscope.common.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;

public class CreateGroup extends ZooKeeperInstance {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        CreateGroup cg = new CreateGroup();
        cg.createZKInstance();
        cg.createPNode("/ZKGroup");
        cg.ZKclose();
    }

    //创建组
    //参数：groupPath
    public void createPNode(String groupPath) throws KeeperException, InterruptedException {
        //创建组
        String cGroupPath = zk.create(groupPath, "group".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //输出组路径
        System.out.println("创建的组路径为：" + cGroupPath);
    }


}
