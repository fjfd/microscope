package com.vipshop.microscope.storage.hbase.repository;

import com.vipshop.microscope.storage.hbase.table.HomeSystemTable;
import com.vipshop.microscope.trace.Constants;
import com.vipshop.microscope.trace.metrics.SystemMetric;
import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Repository
public class HomeSystemRepository extends AbstraceRepository {

    public void initialize() {
        super.initialize(HomeSystemTable.TABLE_NAME, HomeSystemTable.CF);
    }

    public void drop() {
        super.drop(HomeSystemTable.TABLE_NAME);
    }

    /**
     * Store system info.
     *
     * @param info
     */
    public void save(final SystemMetric info) {
        hbaseTemplate.execute(HomeSystemTable.TABLE_NAME, new TableCallback<SystemMetric>() {
            @Override
            public SystemMetric doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes(HomeSystemTable.rowKey(info)));
                p.add(HomeSystemTable.BYTE_CF, HomeSystemTable.BYTE_C_SYSTEM, SerializationUtils.serialize((Serializable) info));
                table.put(p);
                return info;
            }
        });
    }

    /**
     * find system info.
     *
     * @param query
     * @return
     */
    public SystemMetric find(Map<String, String> query) {

        String rowName = query.get(Constants.APP) + query.get(Constants.IP);

        return hbaseTemplate.get(HomeSystemTable.TABLE_NAME, rowName, new RowMapper<SystemMetric>() {
            @SuppressWarnings("unchecked")
            @Override
            public SystemMetric mapRow(Result result, int rowNum) throws Exception {
                return (SystemMetric) SerializationUtils.deserialize(result.getValue(HomeSystemTable.BYTE_CF, HomeSystemTable.BYTE_C_SYSTEM));
            }
        });
    }

}
