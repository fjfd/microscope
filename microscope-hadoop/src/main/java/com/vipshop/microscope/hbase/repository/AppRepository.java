package com.vipshop.microscope.hbase.repository;

import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.hbase.core.HbaseRepository;
import com.vipshop.microscope.hbase.domain.App;

@Repository
public class AppRepository extends HbaseRepository {

	private String tableName = App.TABLE_NAME;
	private String cfInfo = App.CF_INFO;

	private byte[] CF_INFO = Bytes.toBytes(App.CF_INFO);
	private byte[] cAppName = Bytes.toBytes(App.CF_INFO_APP_NAME);
	
	public void initialize() {
		super.initialize(tableName, cfInfo);
	}
	
	public void drop() {
		super.drop(tableName);
	}
	
	public void save(final App appIndex) {
		hbaseTemplate.execute(tableName, new TableCallback<App>() {
			@Override
			public App doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(appIndex.getAppName()));
				p.add(CF_INFO, cAppName, Bytes.toBytes(appIndex.getAppName()));
				table.put(p);
				return appIndex;
			}
		});
	}
	
	public boolean exist(final String appName) {
		return hbaseTemplate.get(tableName, appName, new RowMapper<Boolean>() {
			@Override
			public Boolean mapRow(Result result, int rowNum) throws Exception {
				byte[] name = result.getValue(CF_INFO, cAppName);
				if (name == null) {
					return false;
				}
				return true;
			}
		});
	}
	
	public List<App> findAll() {
		return hbaseTemplate.find(tableName, cfInfo, new RowMapper<App>() {
			@Override
			public App mapRow(Result result, int rowNum) throws Exception {
				return new App(Bytes.toString(result.getValue(CF_INFO, cAppName)));
			}
		});
	}

}
