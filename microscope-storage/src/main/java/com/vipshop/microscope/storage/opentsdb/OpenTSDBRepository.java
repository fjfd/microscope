package com.vipshop.microscope.storage.opentsdb;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;
import org.hbase.async.HBaseClient;

import com.vipshop.microscope.storage.asynchbase.AsyncHBaseClient;
import com.vipshop.microscope.storage.hbase.table.TSDBTable;
import com.vipshop.microscope.storage.hbase.table.TSDBUIDTable;
import com.vipshop.microscope.storage.opentsdb.core.Aggregator;
import com.vipshop.microscope.storage.opentsdb.core.Const;
import com.vipshop.microscope.storage.opentsdb.core.DataPoints;
import com.vipshop.microscope.storage.opentsdb.core.Query;
import com.vipshop.microscope.storage.opentsdb.core.TSDB;
import com.vipshop.microscope.storage.opentsdb.uid.UniqueId;

public class OpenTSDBRepository {
	
	private final HBaseClient client = AsyncHBaseClient.getBaseClient();

	private final UniqueId uniqueId = new UniqueId(client, Bytes.toBytes(TSDBUIDTable.TABLE_NAME), "metrics", 3);
	private final TSDB tsdb = new TSDB(client, TSDBTable.TABLE_NAME, TSDBUIDTable.TABLE_NAME);
	private final Query query = tsdb.newQuery();

	public void add(final String metric, final long timestamp, final long value, final Map<String, String> tags) {
		uniqueId.getOrCreateId(metric);
		tsdb.addPoint(metric, timestamp / 1000, value, tags);
	}
	
	public void add(final String metric, final long timestamp, final double value, final Map<String, String> tags) {
		uniqueId.getOrCreateId(metric);
		tsdb.addPoint(metric, timestamp / 1000, value, tags);
	}
	
	public void add(final String metric, final long timestamp, final float value, final Map<String, String> tags) {
		uniqueId.getOrCreateId(metric);
		tsdb.addPoint(metric, timestamp / 1000, value, tags);
	}
	
	public List<String> suggestMetrics(final String search) {
		return tsdb.suggestMetrics(search);
	}
	
	public List<String> suggestTagNames(final String search) {
		return tsdb.suggestTagNames(search);
	}
	
	public List<String> suggestTagValues(final String search) {
		return tsdb.suggestTagValues(search);
	}
	
	public DataPoints[] find(long starttimestamp, 
			 String metric, Map<String, String> tags,
			 Aggregator function, boolean rate ){
		
		starttimestamp = starttimestamp / 1000;
		
		query.setStartTime((starttimestamp - (starttimestamp % Const.MAX_TIMESPAN)));
		query.setStartTime(starttimestamp);
		query.setTimeSeries(metric, tags, function, rate);
		
		return query.run();
	}
	
	public DataPoints[] find(long starttimestamp, long endtimestamp, 
							 String metric, Map<String, String> tags,
							 Aggregator function, boolean rate ) {
		
		starttimestamp = starttimestamp / 1000;
		endtimestamp = endtimestamp / 1000;
		
		query.setStartTime((starttimestamp - (starttimestamp % Const.MAX_TIMESPAN)));
		query.setEndTime((endtimestamp - (endtimestamp % Const.MAX_TIMESPAN)));
		query.setTimeSeries(metric, tags, function, rate);
		
		return query.run();
	}
	
}
