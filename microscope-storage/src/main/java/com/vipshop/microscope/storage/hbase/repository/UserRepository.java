package com.vipshop.microscope.storage.hbase.repository;

import com.vipshop.microscope.storage.hbase.table.UserTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository extends AbstraceRepository {

    public void initialize() {
        super.initialize(UserTable.TABLE_NAME, new String[]{UserTable.CF_INFO, UserTable.CF_HISTORY});
    }

    public void drop() {
        super.drop(UserTable.TABLE_NAME);
    }

    public void save(final Map<String, String> user) {
        hbaseTemplate.execute(UserTable.TABLE_NAME, new TableCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(UserTable.rowKey(user));
                p.add(UserTable.BYTE_CF_INFO, UserTable.BYTE_C_INFO_USER, Bytes.toBytes(user.get("username")));
                String timestamp = String.valueOf(System.currentTimeMillis());
                p.add(UserTable.BYTE_CF_HISTORY, Bytes.toBytes(timestamp), Bytes.toBytes(user.get("history")));

                table.put(p);
                return user;
            }
        });
    }

    public List<Map<String, Object>> findUserHistory() {
        Scan scan = new Scan();
        return hbaseTemplate.find(UserTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
            @Override
            public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
                Map<String, Object> user = new HashMap<String, Object>();
                user.put("username", Bytes.toString(result.getValue(UserTable.BYTE_CF_INFO, UserTable.BYTE_C_INFO_USER)));
                String[] historyQunitifer = getColumnsInColumnFamily(result, UserTable.CF_HISTORY);
                StringBuilder builder = new StringBuilder();
                for (String hist : historyQunitifer) {
                    builder.append("date=").append(hist).append(":");
                    builder.append("url=").append(Bytes.toString(result.getValue(UserTable.BYTE_CF_HISTORY, Bytes.toBytes(hist))));
                    builder.append(";");
                }
                user.put("history", builder.toString());
                return user;
            }
        });
    }

}
