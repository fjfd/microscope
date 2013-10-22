package com.vipshop.microscope.hbase.repository;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class HbaseRepository implements InitializingBean {

	@Resource(name = "hbaseConfiguration")
	protected Configuration config;

	@Autowired
	protected HbaseTemplate hbaseTemplate;

	protected HBaseAdmin admin;

	@Override
	public void afterPropertiesSet() throws Exception {
		admin = new HBaseAdmin(config);
	}
	
	public void initialize(String tableName, String cfName) { 
		try {
			if (!admin.tableExists(tableName)) {
				HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
				HColumnDescriptor columnDescriptor = new HColumnDescriptor(cfName);
				tableDescriptor.addFamily(columnDescriptor);
				admin.createTable(tableDescriptor);
			}
		} catch (IOException e) {
			throw new RuntimeException("initialize " + tableName, e);
		}
	}
	
	public void drop(String tableName) {
		byte[] tableNameAsBytes = Bytes.toBytes(tableName);
		try {
			if (!admin.isTableDisabled(tableNameAsBytes)) {
				admin.disableTable(tableNameAsBytes);
			}
			admin.deleteTable(tableNameAsBytes);
		} catch (IOException e) {
			throw new RuntimeException("drop" + tableName, e);
		}
	}
	
	public Configuration getConfiguration() {
		return config;
	}

}
