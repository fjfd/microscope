package com.vipshop.microscope.storage.asynchbase;

import org.hbase.async.HBaseClient;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.ThreadPoolUtil;

public class AsyncHBaseClient {
	
	private static final ConfigurationUtil config = ConfigurationUtil.getConfiguration("hbase.properties");
	
	private static final String DEFAULT_ZK_DIR = "/hbase";
	private static final String DEFAULT_ZK_QUORUM = config.getString("zk.host");
	private static final int DEFAULT_SIZE = Runtime.getRuntime().availableProcessors();
	
	private static final HBaseClient client = new HBaseClient(DEFAULT_ZK_QUORUM, 
															  DEFAULT_ZK_DIR, 
															  ThreadPoolUtil.newFixedThreadPool(DEFAULT_SIZE, "asynchbaseclient"));
	
	public static HBaseClient getBaseClient() {
		return client;
	}
}