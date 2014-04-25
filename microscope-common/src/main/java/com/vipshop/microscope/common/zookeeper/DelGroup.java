package com.vipshop.microscope.common.zookeeper;

import java.io.IOException;
import java.util.List;
 
import org.apache.zookeeper.KeeperException;
 
 public class DelGroup extends ZooKeeperInstance {
     public void delete(String groupPath) throws KeeperException, InterruptedException{
		List<String> children=zk.getChildren(groupPath, false);
		//如果不空，则进行删除操作
		if(!children.isEmpty()){
			//删除所有孩子节点
			for(String child:children)
				zk.delete(groupPath+"/"+child, -1);
		}
		//删除组目录节点
		zk.delete(groupPath, -1);
	}
	
	public static void main(String args[]) throws IOException, KeeperException, InterruptedException{
		DelGroup dg=new DelGroup();
		dg.createZKInstance();
		dg.delete("/ZKGroup");
		dg.ZKclose();	
	}
}
