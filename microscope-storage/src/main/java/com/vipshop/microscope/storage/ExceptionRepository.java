package com.vipshop.microscope.storage;

import com.vipshop.microscope.common.cons.Constants;
import com.vipshop.microscope.client.exception.ExceptionData;
import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ExceptionRepository extends AbstractRepository {

    public void create() {
        super.create(ExceptionTable.TABLE_EXCEPTION_INDEX, new String[]{ExceptionTable.CF_APP, ExceptionTable.CF_IP, ExceptionTable.CF_EXCEPTION});
        super.create(ExceptionTable.TABLE_EXCEPTION, ExceptionTable.CF);
    }

    public void drop() {
        super.drop(ExceptionTable.TABLE_EXCEPTION_INDEX);
        super.drop(ExceptionTable.TABLE_EXCEPTION);
    }

    /**
     * Store exception data.
     *
     * @param exception
     */
    public void save(final ExceptionData exception) {

        hbaseTemplate.execute(ExceptionTable.TABLE_EXCEPTION_INDEX, new TableCallback<ExceptionData>() {
            @Override
            public ExceptionData doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(ExceptionTable.rowKeyIndex(exception));
                p.add(ExceptionTable.BYTE_CF_APP, Bytes.toBytes(exception.getApp()), Bytes.toBytes(exception.getApp()));
                p.add(ExceptionTable.BYTE_CF_IP, Bytes.toBytes(exception.getIp()), Bytes.toBytes(exception.getIp()));
                p.add(ExceptionTable.BYTE_CF_EXCEPTION, Bytes.toBytes(exception.getExceptionName()), Bytes.toBytes(exception.getExceptionName()));
                table.put(p);
                return exception;
            }
        });

        hbaseTemplate.execute(ExceptionTable.TABLE_EXCEPTION, new TableCallback<ExceptionData>() {
            @Override
            public ExceptionData doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes(ExceptionTable.rowKey(exception)));
                p.add(ExceptionTable.BYTE_CF, ExceptionTable.BYTE_C_EXCEPTION, SerializationUtils.serialize(exception));
                table.put(p);
                return exception;
            }
        });
    }

    /**
     * Returns App, IP, ExceptionName in follow format:
     * <p/>
     * [
     * "App"   :   app name a,
     * "IP"    :   ["ip adress 1", "ip adress 2", ...],
     * "Name"  :   ["name 1",      "name 2",      ...],
     * ]
     * <p/>
     * [
     * "App"   :   app name b,
     * "IP"    :   ["ip adress 1", "ip adress 2", ...],
     * "Name"  :   ["name 1",      "name 2",      ...],
     * ]
     * <p/>
     * ...
     *
     * @return
     */
    public List<Map<String, Object>> find() {
        final List<String> appList = new ArrayList<String>();
        hbaseTemplate.find(ExceptionTable.TABLE_EXCEPTION_INDEX, ExceptionTable.CF_APP, new RowMapper<List<String>>() {
            @Override
            public List<String> mapRow(Result result, int rowNum) throws Exception {
                String[] qualifiers = getColumnsInColumnFamily(result, ExceptionTable.CF_APP);
                for (int i = 0; i < qualifiers.length; i++) {
                    appList.add(qualifiers[i]);
                }
                return appList;
            }
        });

        final List<Map<String, Object>> appIPNameList = new ArrayList<Map<String, Object>>();
        for (final String row : appList) {
            hbaseTemplate.get(ExceptionTable.TABLE_EXCEPTION_INDEX, row, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
                    Map<String, Object> appTrace = new HashMap<String, Object>();
                    String[] ipQualifiers = getColumnsInColumnFamily(result, ExceptionTable.CF_IP);
                    String[] nameQualifiers = getColumnsInColumnFamily(result, ExceptionTable.CF_EXCEPTION);
                    appTrace.put("app", row);
                    appTrace.put("ip", Arrays.asList(ipQualifiers));
                    appTrace.put("name", Arrays.asList(nameQualifiers));
                    appIPNameList.add(appTrace);
                    return appTrace;
                }
            });
        }

        return appIPNameList;
    }

    public List<ExceptionData> find(Map<String, String> query) {
        Scan scan = new Scan();

        long limit = Long.valueOf(query.get("limit"));

        if (limit > 1000) {
            limit = 1000;
        }

        if (limit < 1) {
            limit = 10;
        }

        PageFilter pageFilter = new PageFilter(limit);
        scan.setFilter(pageFilter);

        /**
         * Query by rowKey : app-ip-exception-time
         */
        String app = query.get(Constants.APP);
        String ip = query.get(Constants.IP);
        String name = query.get(Constants.NAME);

        long startTime = Long.valueOf(query.get(Constants.STARTTIME));
        long endTime = Long.valueOf(query.get(Constants.ENDTIME));

        String startKey = app + "-" + ip + "-" + name + "-" + (Long.MAX_VALUE - endTime);
        String endKey = app + "-" + ip + "-" + name + "-" + (Long.MAX_VALUE - startTime);

        scan.setStartRow(Bytes.toBytes(startKey));
        scan.setStopRow(Bytes.toBytes(endKey));

        List<ExceptionData> exceptions = hbaseTemplate.find(ExceptionTable.TABLE_EXCEPTION, scan, new RowMapper<ExceptionData>() {
            @Override
            public ExceptionData mapRow(Result result, int rowNum) throws Exception {
                return (ExceptionData) SerializationUtils.deserialize(result.getValue(ExceptionTable.BYTE_CF, ExceptionTable.BYTE_C_EXCEPTION));
            }
        });

        return exceptions;
    }
}
