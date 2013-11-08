package com.vipshop.microscope.hbase.repository;

import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.hbase.domain.StatByType;

@Repository
public class StatByTypeRepository extends AbstraceHbaseRepository {

	private String tableName = "stat_by_type";
	private String cf = "cf";
	
	private byte[] CF = Bytes.toBytes("cf");
	private byte[] CF_TYPE = Bytes.toBytes("type");
	private byte[] CF_TOTAL_COUNT = Bytes.toBytes("total_count");
	private byte[] CF_FAILURE_COUNT = Bytes.toBytes("failure_count");
	private byte[] CF_FAILURE_PRECENT = Bytes.toBytes("failure_precent");
	private byte[] CF_MIN = Bytes.toBytes("min");
	private byte[] CF_MAX = Bytes.toBytes("max");
	private byte[] CF_AVG = Bytes.toBytes("avg");
	private byte[] CF_TPS = Bytes.toBytes("tps");


	public void initialize() {
		super.initialize(tableName, cf);
	}

	public void drop() {
		super.drop(tableName);
	}

	public void save(final StatByType statByType) {
		hbaseTemplate.execute(tableName, new TableCallback<StatByType>() {
			@Override
			public StatByType doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(statByType.getType()));
				p.add(CF, CF_TYPE, Bytes.toBytes(statByType.getType()));
				p.add(CF, CF_TOTAL_COUNT, Bytes.toBytes(statByType.getTotalCount()));
				p.add(CF, CF_FAILURE_COUNT, Bytes.toBytes(statByType.getFailureCount()));
				p.add(CF, CF_FAILURE_PRECENT, Bytes.toBytes(statByType.getFailurePrecent()));
				p.add(CF, CF_MIN, Bytes.toBytes(statByType.getMin()));
				p.add(CF, CF_MAX, Bytes.toBytes(statByType.getMax()));
				p.add(CF, CF_AVG, Bytes.toBytes(statByType.getAvg()));
				p.add(CF, CF_TPS, Bytes.toBytes(statByType.getTPS()));
				table.put(p);
				return statByType;
			}
		});
	}
	
	public List<StatByType> findAll() {
		return hbaseTemplate.find(tableName, cf, new RowMapper<StatByType>() {
			@Override
			public StatByType mapRow(Result result, int rowNum) throws Exception {
				return new StatByType(Bytes.toString(result.getValue(CF, CF_TYPE)), 
						  Bytes.toLong(result.getValue(CF, CF_TOTAL_COUNT)),
						  Bytes.toLong(result.getValue(CF, CF_FAILURE_COUNT)), 
						  Bytes.toFloat(result.getValue(CF, CF_FAILURE_PRECENT)),
						  Bytes.toInt(result.getValue(CF, CF_MIN)),
						  Bytes.toInt(result.getValue(CF, CF_MAX)),
						  Bytes.toInt(result.getValue(CF, CF_AVG)),
						  Bytes.toFloat(result.getValue(CF, CF_TPS)));
				}
		});
	}
	
	public StatByType findByType(String type) {
		return hbaseTemplate.get(tableName, type, new RowMapper<StatByType>() {
			@Override
			public StatByType mapRow(Result result, int rowNum) throws Exception {
				return new StatByType(Bytes.toString(result.getValue(CF, CF_TYPE)), 
						  Bytes.toLong(result.getValue(CF, CF_TOTAL_COUNT)),
						  Bytes.toLong(result.getValue(CF, CF_FAILURE_COUNT)), 
						  Bytes.toFloat(result.getValue(CF, CF_FAILURE_PRECENT)),
						  Bytes.toInt(result.getValue(CF, CF_MIN)),
						  Bytes.toInt(result.getValue(CF, CF_MAX)),
						  Bytes.toInt(result.getValue(CF, CF_AVG)),
						  Bytes.toFloat(result.getValue(CF, CF_TPS)));
				}
		});
	}


}
