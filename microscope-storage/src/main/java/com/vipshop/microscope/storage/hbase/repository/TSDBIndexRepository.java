package com.vipshop.microscope.storage.hbase.repository;

import com.vipshop.microscope.common.logentry.Constants;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.storage.hbase.table.TSDBIndexTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TSDBIndexRepository extends AbstraceRepository {

    /**
     * Create table
     */
    public void initialize() {
        super.initialize(TSDBIndexTable.TABLE_NAME, new String[]{TSDBIndexTable.CF_APP,
                                                                 TSDBIndexTable.CF_IP,
                                                                 TSDBIndexTable.CF_METRICS_1,
                                                                 TSDBIndexTable.CF_METRICS_2,
                                                                 TSDBIndexTable.CF_METRICS_3,
                                                                 TSDBIndexTable.CF_METRICS_4,});
    }

    /**
     * Drop table
     */
    public void drop() {
        super.drop(TSDBIndexTable.TABLE_NAME);
    }

    /**
     * Save metrics index
     *
     * @param metric
     */
    public void save(final Metric metric) {
        hbaseTemplate.execute(TSDBIndexTable.TABLE_NAME, new TableCallback<Metric>() {
            @Override
            public Metric doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes(metric.getTags().get(Constants.APP)));
                p.add(TSDBIndexTable.BYTE_CF_APP, Bytes.toBytes(metric.getTags().get(Constants.APP)), Bytes.toBytes(metric.getTags().get(Constants.APP)));
                p.add(TSDBIndexTable.BYTE_CF_IP, Bytes.toBytes(metric.getTags().get(Constants.IP)), Bytes.toBytes(metric.getTags().get(Constants.IP)));

                String name = metric.getMetric();

                List<String> names = Arrays.asList(name.split("\\."));

                if (names.size() == 1) {
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_1, Bytes.toBytes(names.get(0)), Bytes.toBytes(names.get(0)));
                }

                if (names.size() == 2) {
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_1, Bytes.toBytes(names.get(0)), Bytes.toBytes(names.get(0)));
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_2, Bytes.toBytes(names.get(0) + "." + names.get(1)),
                                                            Bytes.toBytes(names.get(0) + "." + names.get(1)));
                }

                if (names.size() == 3) {
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_1, Bytes.toBytes(names.get(0)), Bytes.toBytes(names.get(0)));
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_2, Bytes.toBytes(names.get(0) + "." + names.get(1)),
                                                            Bytes.toBytes(names.get(0) + "." + names.get(1)));
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_3, Bytes.toBytes(names.get(0) + "." + names.get(1) + "." + names.get(2)),
                                                            Bytes.toBytes(names.get(0) + "." + names.get(1) + "." + names.get(2)));
                }

                if (names.size() == 4) {
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_1, Bytes.toBytes(names.get(0)), Bytes.toBytes(names.get(0)));
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_2, Bytes.toBytes(names.get(0) + "." + names.get(1)),
                                                            Bytes.toBytes(names.get(0) + "." + names.get(1)));
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_3, Bytes.toBytes(names.get(0) + "." + names.get(1) + "." + names.get(2)),
                                                            Bytes.toBytes(names.get(0) + "." + names.get(1) + "." + names.get(2)));
                    p.add(TSDBIndexTable.BYTE_CF_METRICS_4, Bytes.toBytes(names.get(0) + "." + names.get(1) + "." + names.get(2) + "." + names.get(3)),
                                                            Bytes.toBytes(names.get(0) + "." + names.get(1) + "." + names.get(2) + "." + names.get(3)));
                }

                table.put(p);
                return metric;
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
        hbaseTemplate.find(TSDBIndexTable.TABLE_NAME, TSDBIndexTable.CF_APP, new RowMapper<List<String>>() {
            @Override
            public List<String> mapRow(Result result, int rowNum) throws Exception {
                String[] appQunitifer = getColumnsInColumnFamily(result, TSDBIndexTable.CF_APP);
                for (int i = 0; i < appQunitifer.length; i++) {
                    appList.add(appQunitifer[i]);
                }
                return appList;
            }
        });

        final List<Map<String, Object>> appIPNameList = new ArrayList<Map<String, Object>>();
        for (final String row : appList) {
            hbaseTemplate.get(TSDBIndexTable.TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
                    Map<String, Object> metrics = new LinkedHashMap<String, Object>();
                    String[] ipQunitifer = getColumnsInColumnFamily(result, TSDBIndexTable.CF_IP);
                    String[] nameQunitifer1 = getColumnsInColumnFamily(result, TSDBIndexTable.CF_METRICS_1);
                    String[] nameQunitifer2 = getColumnsInColumnFamily(result, TSDBIndexTable.CF_METRICS_2);
                    String[] nameQunitifer3 = getColumnsInColumnFamily(result, TSDBIndexTable.CF_METRICS_3);
                    String[] nameQunitifer4 = getColumnsInColumnFamily(result, TSDBIndexTable.CF_METRICS_4);
                    metrics.put("app", row);
                    metrics.put("ip", Arrays.asList(ipQunitifer));
                    metrics.put("name1", Arrays.asList(nameQunitifer1));

                    List<String> name2List = new ArrayList<String>();
                    if (nameQunitifer1 != null) {
                        String firstName = nameQunitifer1[0];
                        if (nameQunitifer2 != null) {
                            for (String name : nameQunitifer2) {
                                if (name.startsWith(firstName)) {
                                    name2List.add(name);
                                }
                            }
                        }
                    }

                    metrics.put("name2", name2List);

                    List<String> name3List = new ArrayList<String>();
                    if (name2List != null) {
                        String firstName = name2List.get(0);
                        if (nameQunitifer3 != null) {
                            for (String name : nameQunitifer3) {
                                if (name.startsWith(firstName)) {
                                    name3List.add(name);
                                }
                            }
                        }
                    }

                    metrics.put("name3", name3List);

                    List<String> name4List = new ArrayList<String>();
                    if (name3List != null) {
                        for (String name : name3List) {
                            if (nameQunitifer4 != null) {
                                for (String quenitifer : nameQunitifer4) {
                                    if (quenitifer.startsWith(name)) {
                                        name4List.add(quenitifer);
                                    }
                                }
                            }
                        }
                    }

                    metrics.put("name4", name4List);

                    appIPNameList.add(metrics);
                    return metrics;
                }
            });
        }

        return appIPNameList;
    }

}
