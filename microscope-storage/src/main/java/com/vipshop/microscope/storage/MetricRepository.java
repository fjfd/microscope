package com.vipshop.microscope.storage;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.metric.MetricData;
import net.opentsdb.core.*;
import org.hbase.async.HBaseClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MetricRepository extends AbstractRepository {

    private final ConfigurationUtil config = ConfigurationUtil.getConfiguration("hbase.properties");

    private final String DEFAULT_ZK_DIR = "/hbase";
    private final String DEFAULT_ZK_QUORUM = config.getString("zk.host");
    private final int DEFAULT_SIZE = Runtime.getRuntime().availableProcessors();

    private final HBaseClient client = new HBaseClient(DEFAULT_ZK_QUORUM, DEFAULT_ZK_DIR,
            ThreadPoolUtil.newFixedThreadPool(DEFAULT_SIZE, "async-hbaseclient"));

    private final TSDB tsdb = new TSDB(client, MetricTable.TABLE_TSDB, MetricTable.TABLE_TSDBUID);

    private final Query query = tsdb.newQuery();

    public void create() {
        super.create(MetricTable.TABLE_TSDB, MetricTable.CF_T);
        super.create(MetricTable.TABLE_TSDBUID, new String[]{MetricTable.CF_NAME, MetricTable.CF_ID});
    }

    public void drop() {
        super.drop(MetricTable.TABLE_TSDB);
        super.drop(MetricTable.TABLE_TSDBUID);
    }

    public void save(final MetricData metrics) {

        String metric = metrics.getMetric();
        long timestamp = metrics.getTimestamp();
        Map<String, String> tags = metrics.getTags();
        Object value = metrics.getValue();

        if (value instanceof Long) {
            tsdb.addPoint(metric, timestamp / 1000, ((Long) value).longValue(), tags);
            return;
        }

        if (value instanceof Double) {
            tsdb.addPoint(metric, timestamp / 1000, ((Double) value).doubleValue(), tags);
            return;
        }

        if (value instanceof Float) {
            tsdb.addPoint(metric, timestamp / 1000, ((Float) value).floatValue(), tags);
            return;
        }

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
