package com.vipshop.microscope.storage.hbase;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class TraceOverviewRepository extends AbstraceRepository {

    public void initialize() {
        super.initialize(TraceOverviewTable.TABLE_NAME, TraceOverviewTable.CF);
    }

    public void drop() {
        super.drop(TraceOverviewTable.TABLE_NAME);
    }

    public void save(final TraceOverviewTable tableTrace) {
        hbaseTemplate.execute(TraceOverviewTable.TABLE_NAME, new TableCallback<TraceOverviewTable>() {
            @Override
            public TraceOverviewTable doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes(tableTrace.rowKey()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_APP_NAME, Bytes.toBytes(tableTrace.getAppName()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_IP_ADDRESS, Bytes.toBytes(tableTrace.getIPAdress()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TRACE_NAME, Bytes.toBytes(tableTrace.getTraceName()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TRACE_ID, Bytes.toBytes(tableTrace.getTraceId()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_START_TIMESTAMP, Bytes.toBytes(tableTrace.getStartTimestamp()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_END_TIMESTAMP, Bytes.toBytes(tableTrace.getEndTimestamp()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_DURATION, Bytes.toBytes(tableTrace.getDuration()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_RESULT_CODE, Bytes.toBytes(tableTrace.getResultCode()));
                p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TYPE, Bytes.toBytes(tableTrace.getType()));
                table.put(p);
                return tableTrace;
            }
        });
    }

    public void save(final List<TraceOverviewTable> tableTraces) {
        hbaseTemplate.execute(TraceOverviewTable.TABLE_NAME, new TableCallback<List<TraceOverviewTable>>() {
            @Override
            public List<TraceOverviewTable> doInTable(HTableInterface table) throws Throwable {
                List<Put> puts = new ArrayList<Put>();
                for (TraceOverviewTable tableTrace : tableTraces) {
                    Put p = new Put(Bytes.toBytes(tableTrace.getTraceId()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_APP_NAME, Bytes.toBytes(tableTrace.getAppName()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_IP_ADDRESS, Bytes.toBytes(tableTrace.getIPAdress()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TRACE_NAME, Bytes.toBytes(tableTrace.getTraceName()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TRACE_ID, Bytes.toBytes(tableTrace.getTraceId()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_START_TIMESTAMP, Bytes.toBytes(tableTrace.getStartTimestamp()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_END_TIMESTAMP, Bytes.toBytes(tableTrace.getEndTimestamp()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_DURATION, Bytes.toBytes(tableTrace.getDuration()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_RESULT_CODE, Bytes.toBytes(tableTrace.getResultCode()));
                    p.add(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TYPE, Bytes.toBytes(tableTrace.getType()));
                    puts.add(p);
                }
                table.put(puts);
                return tableTraces;
            }
        });
    }

    public List<TraceOverviewTable> find(Map<String, String> query) {
        Scan scan = new Scan();

        /**
         * limit the size of result in [10, 1000]
         */
        long limit = Long.valueOf(query.get("limit"));

        if (limit > 1000) {
            limit = 1000;
        }

        if (limit < 1) {
            limit = 10;
        }

        PageFilter pageFilter = new PageFilter(limit);
        scan.setFilter(pageFilter);

        String appName = query.get("appName");
        String ipAddress = query.get("ipAddress");
        String traceName = query.get("traceName");

        long startTime = Long.valueOf(query.get("startTime"));
        long endTime = Long.valueOf(query.get("endTime"));

		/*
		 * Query by rowKey : appName-traceName-timestamp-ipAddress-******
		 */
        String startKey = appName + "-" + traceName + "-" + ipAddress + "-" + (Long.MAX_VALUE - endTime);
        String endKey = appName + "-" + traceName + "-" + ipAddress + "-" + (Long.MAX_VALUE - startTime);
		
		/*
		 * if ipAdress equals "All", remove ipAddress from query condition.
		 */
        if (ipAddress.equals("All")) {
			/*
			 * Query by rowKey : appName-traceName-timestamp-******
			 */
            startKey = appName + "-" + traceName + "-" + (Long.MAX_VALUE - endTime);
            endKey = appName + "-" + traceName + "-" + (Long.MAX_VALUE - startTime);
        }

        scan.setStartRow(Bytes.toBytes(startKey));
        scan.setStopRow(Bytes.toBytes(endKey));

        scan.setCaching(100);
        scan.setCacheBlocks(true);

        List<TraceOverviewTable> tableTraces = hbaseTemplate.find(TraceOverviewTable.TABLE_NAME, scan, new RowMapper<TraceOverviewTable>() {
            @Override
            public TraceOverviewTable mapRow(Result result, int rowNum) throws Exception {
                return new TraceOverviewTable(
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_APP_NAME)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_IP_ADDRESS)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TRACE_NAME)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TRACE_ID)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_START_TIMESTAMP)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_END_TIMESTAMP)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_DURATION)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_RESULT_CODE)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TYPE))
                );
            }
        });

        Collections.sort(tableTraces);
        return tableTraces;
    }

    public List<TraceOverviewTable> find(Scan scan) {
        List<TraceOverviewTable> tableTraces = hbaseTemplate.find(TraceOverviewTable.TABLE_NAME, scan, new RowMapper<TraceOverviewTable>() {
            @Override
            public TraceOverviewTable mapRow(Result result, int rowNum) throws Exception {
                return new TraceOverviewTable(
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_APP_NAME)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_IP_ADDRESS)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TRACE_NAME)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TRACE_ID)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_START_TIMESTAMP)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_END_TIMESTAMP)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_DURATION)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_RESULT_CODE)),
                        Bytes.toString(result.getValue(TraceOverviewTable.BYTE_CF, TraceOverviewTable.BYTE_C_TYPE)));
            }
        });

        Collections.sort(tableTraces);
        return tableTraces;
    }

}
