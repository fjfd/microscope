package com.vipshop.microscope.storage.tsdb;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.storage.hbase.TSDBTable;
import com.vipshop.microscope.storage.hbase.TSDBUIDTable;
import net.opentsdb.core.*;
import org.hbase.async.HBaseClient;

import java.util.List;
import java.util.Map;

public class TSDBRepository {

    private final ConfigurationUtil config = ConfigurationUtil.getConfiguration("hbase.properties");

    private final String DEFAULT_ZK_DIR = "/hbase";
    private final String DEFAULT_ZK_QUORUM = config.getString("zk.host");
    private final int DEFAULT_SIZE = Runtime.getRuntime().availableProcessors();

    private final HBaseClient client = new HBaseClient(DEFAULT_ZK_QUORUM,
            DEFAULT_ZK_DIR,
            ThreadPoolUtil.newFixedThreadPool(DEFAULT_SIZE, "async-hbaseclient"));

    private final TSDB tsdb = new TSDB(client, TSDBTable.TABLE_NAME, TSDBUIDTable.TABLE_NAME);

    private final Query query = tsdb.newQuery();

    public void add(final String metric, final long timestamp, final Object value, final Map<String, String> tags) {

        if (value instanceof Long) {
            add(metric, timestamp, ((Long) value).longValue(), tags);
        }

        if (value instanceof Double) {
            add(metric, timestamp, ((Double) value).doubleValue(), tags);
        }

        if (value instanceof Float) {
            add(metric, timestamp, ((Float) value).floatValue(), tags);
        }

    }

    public void add(final String metric, final long timestamp, final long value, final Map<String, String> tags) {
        tsdb.addPoint(metric, timestamp / 1000, value, tags);
    }

    public void add(final String metric, final long timestamp, final double value, final Map<String, String> tags) {
        tsdb.addPoint(metric, timestamp / 1000, value, tags);
    }

    public void add(final String metric, final long timestamp, final float value, final Map<String, String> tags) {
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

    public DataPoints[] find(long start,
                             String metric, Map<String, String> tags,
                             Aggregator function, boolean rate) {

        start = start / 1000;

        query.setStartTime((start - (start % Const.MAX_TIMESPAN)));
        query.setStartTime(start);
        query.setTimeSeries(metric, tags, function, rate);

        return query.run();
    }

    public DataPoints[] find(long start, long end,
                             String metric, Map<String, String> tags,
                             Aggregator function, boolean rate) {

        start = start / 1000;
        end = end / 1000;

        query.setStartTime((start - (start % Const.MAX_TIMESPAN)));
        query.setEndTime((end - (end % Const.MAX_TIMESPAN)));
        query.setTimeSeries(metric, tags, function, rate);

        return query.run();
    }

}
