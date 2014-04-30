package com.vipshop.microscope.common.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;

public class JoinGroup extends ZooKeeperInstance {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        JoinGroup jg = new JoinGroup();
        jg.createZKInstance();
        jg.MultiJoin();
        jg.ZKclose();
    }

    //加入组操作
    public int Join(String groupPath, int k) throws KeeperException, InterruptedException {
        String child = k + "";
        child = "child_" + child;

        //创建的路径
        String path = groupPath + "/" + child;
        //检查组是否存在
        if (zk.exists(groupPath, true) != null) {
            //如果存在，加入组
            zk.create(path, child.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return 1;
        } else {
            System.out.println("组不存在！");
            return 0;
        }
    }

    //加入组操作
    public void MultiJoin() throws KeeperException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            int k = Join("/ZKGroup", i);
            //如果组不存在则退出
            if (0 == k)
                System.exit(1);
        }
    }
}