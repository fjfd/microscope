package com.vipshop.microscope.collector.server;

import com.vipshop.microscope.common.cfg.Configuration;

public class CollectorConstant {
	
	private static final Configuration config = Configuration.getConfiguration("config.properties");
	
	public static final int COLLECTOR_PORT = config.getInt("collector_port");
	public static final int CONSUMER_POOL_SIZE = config.getInt("consumer_pool_size");
}
