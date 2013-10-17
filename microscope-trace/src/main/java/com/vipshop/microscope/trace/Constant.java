package com.vipshop.microscope.trace;

import com.vipshop.microscope.common.cfg.Configuration;

public class Constant {
	
	private static final Configuration config = Configuration.getConfiguration("trace.properties");
	
	public static final String APP_NAME = config.getString("app_name");
	
	public static final String COLLECTOR_HOST = config.getString("collector_host");
	public static final int COLLECTOR_PORT = config.getInt("collector_port");

	public static final int MAX_BATCH_SIZE = config.getInt("max_batch_size");
	public static final int MAX_EMPTY_SIZE = config.getInt("max_empty_size");

	public static final int SWITCH = config.getInt("switch");

	public static final int QUEUE_SIZE = config.getInt("queue_size");
	
	

}
