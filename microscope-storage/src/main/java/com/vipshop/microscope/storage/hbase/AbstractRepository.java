package com.vipshop.microscope.storage.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.hfile.Compression.Algorithm;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.NavigableMap;

@Component
public abstract class AbstractRepository implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AbstractRepository.class);

    public static final int TIME_TO_LIVE = 2 * 7 * 24 * 60 * 60;

    @Resource(name = "hbaseConfiguration")
    protected Configuration config;

    @Autowired
    protected HbaseTemplate hbaseTemplate;

    protected HBaseAdmin admin;

    @Override
    public void afterPropertiesSet() throws Exception {
        admin = new HBaseAdmin(config);
    }

    /**
     * Create HBase table
     *
     * @param table the name of table
     * @param cf    the name of column family
     */
    protected void create(String table, String cf) {
        try {
            if (!admin.tableExists(table)) {
                HTableDescriptor tableDescriptor = new HTableDescriptor(table);
                HColumnDescriptor columnDescriptor = new HColumnDescriptor(cf);
                columnDescriptor.setMaxVersions(1);
                columnDescriptor.setCompressionType(Algorithm.SNAPPY);
                columnDescriptor.setTimeToLive(TIME_TO_LIVE);
                tableDescriptor.addFamily(columnDescriptor);
                admin.createTable(tableDescriptor);

				/*
                 * close auto flush function.
				 */
                hbaseTemplate.setAutoFlush(false);

                logger.info("create HBase table " + table);
            }
        } catch (IOException e) {
            throw new RuntimeException("error happens when create " + table, e);
        }
    }

    /**
     * Create HBase table
     *
     * @param table the name of table
     * @param cf    the name of column family
     */
    protected void create(String table, String[] cf) {
        try {
            if (!admin.tableExists(table)) {
                HTableDescriptor tableDescriptor = new HTableDescriptor(table);
                for (int i = 0; i < cf.length; i++) {
                    HColumnDescriptor columnDescriptor = new HColumnDescriptor(cf[i]);
                    columnDescriptor.setMaxVersions(1);
                    columnDescriptor.setCompressionType(Algorithm.SNAPPY);
                    columnDescriptor.setTimeToLive(TIME_TO_LIVE);
                    tableDescriptor.addFamily(columnDescriptor);
                }
                admin.createTable(tableDescriptor);
                hbaseTemplate.setAutoFlush(false);

                logger.info("create HBase table " + table);
            }
        } catch (IOException e) {
            throw new RuntimeException("error happens when create " + table, e);
        }
    }

    /**
     * Drop HBase table
     *
     * @param table the name of table
     */
    protected void drop(String table) {
        byte[] tableNameAsBytes = Bytes.toBytes(table);
        try {
            if (!admin.isTableDisabled(tableNameAsBytes)) {
                admin.disableTable(tableNameAsBytes);
            }
            admin.deleteTable(tableNameAsBytes);

            logger.info("drop HBase table " + table);
        } catch (IOException e) {
            throw new RuntimeException("error happens when drop" + table, e);
        }
    }

    /**
     * Get all columns by column family
     *
     * @param r            row result
     * @param ColumnFamily the name of column family
     * @return             columns in array format
     */
    protected String[] getColumnsInColumnFamily(Result r, String ColumnFamily) {
        NavigableMap<byte[], byte[]> familyMap = r.getFamilyMap(Bytes.toBytes(ColumnFamily));
        String[] qualifiers = new String[familyMap.size()];
        int counter = 0;
        for (byte[] qualifier : familyMap.keySet()) {
            qualifiers[counter++] = Bytes.toString(qualifier);
        }
        return qualifiers;
    }

    /**
     * Return Configuration
     *
     * @return config
     */
    public Configuration getConfiguration() {
        return config;
    }

}
