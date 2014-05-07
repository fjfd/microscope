package com.vipshop.microscope.storage;

import com.vipshop.microscope.common.cons.Constants;
import com.vipshop.microscope.trace.system.SystemData;
import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class SystemRepository extends AbstractRepository {

    public void create() {
        super.create(SystemTable.TABLE_NAME, SystemTable.CF);
    }

    public void drop() {
        super.drop(SystemTable.TABLE_NAME);
    }

    /**
     * Store system data.
     *
     * @param system
     */
    public void save(final SystemData system) {
        hbaseTemplate.execute(SystemTable.TABLE_NAME, new TableCallback<SystemData>() {
            @Override
            public SystemData doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes(SystemTable.rowKey(system)));
                p.add(SystemTable.BYTE_CF, SystemTable.BYTE_C_SYSTEM, SerializationUtils.serialize(system));
                table.put(p);
                return system;
            }
        });
    }

    /**
     * get system data.
     *
     * @param query
     * @return
     */
    public SystemData find(Map<String, String> query) {

        String rowName = query.get(Constants.APP) + query.get(Constants.IP);

        return hbaseTemplate.get(SystemTable.TABLE_NAME, rowName, new RowMapper<SystemData>() {
            @Override
            public SystemData mapRow(Result result, int rowNum) throws Exception {
                return (SystemData) SerializationUtils.deserialize(result.getValue(SystemTable.BYTE_CF, SystemTable.BYTE_C_SYSTEM));
            }
        });
    }

}
