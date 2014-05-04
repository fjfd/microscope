package com.vipshop.microscope.storage.hbase.table;

import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.Constants;
import com.vipshop.microscope.trace.metric.MetricData;
import org.apache.hadoop.hbase.util.Bytes;

public class TSDBTable {

    public static final String TABLE_NAME = "tsdb";

    public static final String CF_T = "t";

    public static final byte[] BYTE_CF_T = Bytes.toBytes(CF_T);

    public static byte[] rowKey(MetricData metric) {
        String name = metric.getMetric();
        long baseTime = TimeStampUtil.baseHourTime(metric.getTimestamp());
        String app = metric.getTags().get(Constants.APP);
        String ip = metric.getTags().get(Constants.IP);
        return Bytes.toBytes(name + "#" + baseTime + "#" + app + ip);
    }

    public static byte[] column(MetricData metric) {
        long time = (metric.getTimestamp() / 1000) % Constants.MAX_TIMESPAN;
        return Bytes.toBytes(String.valueOf(time));
    }

    public static byte[] value(MetricData metric) {
        return Bytes.toBytes(String.valueOf(metric.getValue()));
    }


}
