package com.vipshop.microscope.storage.hbase.repository;

import com.vipshop.microscope.storage.hbase.table.TraceIndexTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TraceIndexRepository extends AbstraceRepository {

    public void initialize() {
        super.initialize(TraceIndexTable.TABLE_NAME, new String[]{TraceIndexTable.CF_APP, TraceIndexTable.CF_IP, TraceIndexTable.CF_TRACE});
    }

    public void drop() {
        super.drop(TraceIndexTable.TABLE_NAME);
    }

    /**
     * Save App, IP, traceName.
     * <p/>
     * for example
     * <p/>
     * ROW                COLUMN+CELL
     * user_info          column=cf_app   :user_info,        timestamp=1393830202990,   value=user_info
     * user_info          column=cf_ip    :12456789,         timestamp=1393829977044,   value=12456789
     * user_info          column=cf_trace :www.huohu.com,    timestamp=1393830202990,   value=www.huohu.com
     *
     * @param traceIndex
     */
    public void save(final TraceIndexTable traceIndex) {
        hbaseTemplate.execute(TraceIndexTable.TABLE_NAME, new TableCallback<TraceIndexTable>() {
            @Override
            public TraceIndexTable doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes(traceIndex.getAppName()));
                p.add(TraceIndexTable.BYTE_CF_APP, Bytes.toBytes(traceIndex.getAppName()), Bytes.toBytes(traceIndex.getAppName()));
                p.add(TraceIndexTable.BYTE_CF_IP, Bytes.toBytes(traceIndex.getIpAdress()), Bytes.toBytes(traceIndex.getIpAdress()));
                p.add(TraceIndexTable.BYTE_CF_TRACE, Bytes.toBytes(traceIndex.getTraceName()), Bytes.toBytes(traceIndex.getTraceName()));
                table.put(p);
                return traceIndex;
            }
        });
    }

    /**
     * Save App, IP, traceName.
     * <p/>
     * for example
     * <p/>
     * ROW                COLUMN+CELL
     * user_info          column=cf_app   :user_info,        timestamp=1393830202990,   value=user_info
     * user_info          column=cf_ip    :12456789,         timestamp=1393829977044,   value=12456789
     * user_info          column=cf_trace :www.huohu.com,    timestamp=1393830202990,   value=www.huohu.com
     *
     * @param app
     */
    public void save(final List<TraceIndexTable> appTables) {
        hbaseTemplate.execute(TraceIndexTable.TABLE_NAME, new TableCallback<List<TraceIndexTable>>() {
            @Override
            public List<TraceIndexTable> doInTable(HTableInterface table) throws Throwable {
                List<Put> puts = new ArrayList<Put>();
                for (TraceIndexTable appTable : appTables) {
                    Put p = new Put(Bytes.toBytes(appTable.getAppName()));
                    p.add(TraceIndexTable.BYTE_CF_APP, Bytes.toBytes(appTable.getAppName()), Bytes.toBytes(appTable.getAppName()));
                    p.add(TraceIndexTable.BYTE_CF_IP, Bytes.toBytes(appTable.getIpAdress()), Bytes.toBytes(appTable.getIpAdress()));
                    p.add(TraceIndexTable.BYTE_CF_TRACE, Bytes.toBytes(appTable.getTraceName()), Bytes.toBytes(appTable.getTraceName()));
                    puts.add(p);
                }
                table.put(puts);
                return appTables;
            }
        });
    }


    /**
     * Returns App, IP, traceName in follow format:
     * <p/>
     * [
     * "app"   :   app name a,
     * "ip"    :   ["ip adress 1", "ip adress 2", ...],
     * "trace" :   ["trace name 1", "trace name 2", ...],
     * ]
     * <p/>
     * [
     * "app"   :   app name b,
     * "ip"    :   ["ip adress 1", "ip adress 2", ...],
     * "trace" :   ["trace name 1", "trace name 2", ...],
     * ]
     *
     * @return
     */
    public List<Map<String, Object>> find() {

        final List<String> appList = new ArrayList<String>();
        final List<Map<String, Object>> appTraceList = new ArrayList<Map<String, Object>>();

        hbaseTemplate.find(TraceIndexTable.TABLE_NAME, TraceIndexTable.CF_APP, new RowMapper<List<String>>() {
            @Override
            public List<String> mapRow(Result result, int rowNum) throws Exception {
                String[] appQunitifer = getColumnsInColumnFamily(result, TraceIndexTable.CF_APP);
                for (int i = 0; i < appQunitifer.length; i++) {
                    appList.add(appQunitifer[i]);
                }
                return appList;
            }
        });

        for (Iterator<String> iterator = appList.iterator(); iterator.hasNext(); ) {
            final String row = iterator.next();

            hbaseTemplate.get(TraceIndexTable.TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
                    Map<String, Object> appTrace = new HashMap<String, Object>();
                    String[] ipQunitifer = getColumnsInColumnFamily(result, TraceIndexTable.CF_IP);
                    String[] traceQunitifer = getColumnsInColumnFamily(result, TraceIndexTable.CF_TRACE);
                    appTrace.put("app", row);
                    appTrace.put("ip", Arrays.asList(ipQunitifer));
                    appTrace.put("trace", Arrays.asList(traceQunitifer));
                    appTraceList.add(appTrace);
                    return appTrace;
                }
            });
        }

        return appTraceList;
    }


}
