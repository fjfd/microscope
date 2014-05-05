package com.vipshop.microscope.storage.hbase;

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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository
public class ExceptionRepository extends AbstractRepository {

    public void initialize() {
        super.create(ExceptionTable.TABLE_NAME, ExceptionTable.CF);
    }

    public void drop() {
        super.drop(ExceptionTable.TABLE_NAME);
    }

    /**
     * Store exception stack.
     *
     * @param exception
     */
    public void save(final Map<String, Object> exception) {
        hbaseTemplate.execute(ExceptionTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes(ExceptionTable.rowKey(exception)));
                p.add(ExceptionTable.BYTE_CF, ExceptionTable.BYTE_C_EXCEPTION, SerializationUtils.serialize((Serializable) exception));
                table.put(p);
                return exception;
            }
        });
    }

    public List<Map<String, Object>> find(Map<String, String> query) {
        Scan scan = new Scan();

        /**
         * limit the size of result in [10, 100]
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

        /**
         * Query by rowKey : appName-ipAddress-timestamp
         */
        String appName = query.get("appName");
        String ipAddress = query.get("ipAddress");
        String name = query.get("name");

        long startTime = Long.valueOf(query.get("startTime"));
        long endTime = Long.valueOf(query.get("endTime"));

        String startKey = appName + "-" + ipAddress + "-" + name + "-" + (Long.MAX_VALUE - endTime);
        String endKey = appName + "-" + ipAddress + "-" + name + "-" + (Long.MAX_VALUE - startTime);

        scan.setStartRow(Bytes.toBytes(startKey));
        scan.setStopRow(Bytes.toBytes(endKey));

        List<Map<String, Object>> exceptionList = hbaseTemplate.find(ExceptionTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
            @SuppressWarnings("unchecked")
            @Override
            public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
                return (Map<String, Object>) SerializationUtils.deserialize(result.getValue(ExceptionTable.BYTE_CF, ExceptionTable.BYTE_C_EXCEPTION));
            }
        });

        return exceptionList;
    }
}
