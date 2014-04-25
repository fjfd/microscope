package com.vipshop.microscope.common.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperInstance {
    //会话超时时间，设置为与系统默认时间一致
 	public static final int SESSION_TIMEOUT=30000;
 
	//创建ZooKeeper实例
	ZooKeeper zk;
	
	//创建Watcher实例
	Watcher wh=new Watcher(){
		public void process(WatchedEvent event){
			System.out.println(event.toString());
		}
	};
	
 //初始化Zookeeper实例
	public void createZKInstance() throws IOException{	
		zk=new ZooKeeper("10.100.90.183:2181",ZooKeeperInstance.SESSION_TIMEOUT,this.wh);
	}
	
	//关闭ZK实例
	public void ZKclose() throws InterruptedException{
		zk.close();
	}
}
