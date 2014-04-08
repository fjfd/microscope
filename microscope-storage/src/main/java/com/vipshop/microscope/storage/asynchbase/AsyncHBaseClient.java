package com.vipshop.microscope.storage.asynchbase;

import org.hbase.async.HBaseClient;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.ThreadPoolUtil;

public class AsyncHBaseClient {
	
	public static final ConfigurationUtil config = ConfigurationUtil.getConfiguration("hbase.properties");
	
	public static final String DEFAULT_ZK_DIR = "/hbase";
	public static final String DEFAULT_ZK_QUORUM = config.getString("zk.host");

	private static final HBaseClient client = new HBaseClient(DEFAULT_ZK_QUORUM, DEFAULT_ZK_DIR, ThreadPoolUtil.newFixedThreadPool(10, "hbaseclient"));
	
	public static HBaseClient getBaseClient() {
		return client;
	}
}