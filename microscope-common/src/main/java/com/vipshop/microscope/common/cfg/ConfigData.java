package com.vipshop.microscope.common.cfg;


/**
 * Configurable value for application.
 * 
 * All the configurable value should read from this utility.
 * 
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ConfigData {
	
	private static final Configuration config = Configuration.getConfiguration("config.properties");
	
	public static final int COLLECTOR_PORT = config.getInt("collector_port");
	public static final int CONSUMER_POOL_SIZE = config.getInt("consumer_pool_size");
}
