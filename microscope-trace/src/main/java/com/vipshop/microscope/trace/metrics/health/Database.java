package com.vipshop.microscope.trace.metrics.health;

public interface Database {

	boolean isConnected();

	String getUrl();

}
