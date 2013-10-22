package com.vipshop.microscope.trace.span;


/**
 * Category is a class stands for service type: 
 * 
 * access cache;
 * access db;
 * access rpc service;
 * ...
 * 
 * @author Xu Fei
 * @version 1.0
 */
public enum Category {
	
	ACTION,
	SERVICE,
	DAO,
	CACHE,
	DB,
	SQL,
	RPC,
	METHOD;
}
