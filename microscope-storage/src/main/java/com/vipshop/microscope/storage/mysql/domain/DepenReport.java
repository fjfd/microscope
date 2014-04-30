package com.vipshop.microscope.storage.mysql.domain;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.MathUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.gen.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DepenReport.
 * <p/>
 * From client view: see client depens on service.
 * <p/>
 * client		service	  times  qps  etc...
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * client       serviceA  1000
 * client       serviceB  3000
 * client       serviceC  2000
 * <p/>
 * From server view: see service invoked by clients.
 * <p/>
 * server       client    times  qps  etc...
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * service      clientA   1000
 * service      clientB   3000
 * service      clientC   2000
 *
 * @author Xu Fei
 * @version 1.0
 */
public class DepenReport extends AbstraceReport {

    public static final Logger logger = LoggerFactory.getLogger(DepenReport.class);

    private static final ConcurrentHashMap<String, DepenReport> depenContainer = new ConcurrentHashMap<String, DepenReport>();

    private String clientName;
    private String serverName;

    private long totalCount;
    private int failCount;
    private float failPercent;

    private long sum;

    private float avg;
    private float qps;

    private long startTime;
    private long endTime;

    public void analyze(CalendarUtil calendarUtil, Span span) {
        String key = this.getKey(calendarUtil, span);
        DepenReport report = depenContainer.get(key);
        if (report == null) {
            report = new DepenReport();
            report.updateReportInit(calendarUtil, span);
        }
        report.updateReportNext(span);
        depenContainer.put(key, report);

        // save previous report to mysql and remove form hashmap
        Set<Entry<String, DepenReport>> entries = depenContainer.entrySet();
        for (Entry<String, DepenReport> entry : entries) {
            String prevKey = entry.getKey();
            if (!prevKey.equals(key)) {
                DepenReport prevReport = entry.getValue();
                try {
                    prevReport.saveReport();
                } catch (Exception e) {
                    logger.error("save depen report to mysql error ignore ... " + e);
                } finally {
                    depenContainer.remove(prevKey);
                }
            }
        }

    }

    @Override
    public void updateReportInit(CalendarUtil calendarUtil, Span span) {
        this.setDateByHour(calendarUtil);
        this.setClientName(span.getAppName());
        this.setServerName(span.getServerName());
        this.setStartTime(System.currentTimeMillis());
    }

    @Override
    public void updateReportNext(Span span) {
        this.setSum(this.getSum() + span.getDuration());
        this.setTotalCount(this.getTotalCount() + 1);
        if (!span.getResultCode().equals("OK")) {
            this.setFailCount(this.getFailCount() + 1);
        }
        this.setEndTime(System.currentTimeMillis());
    }

    @Override
    public void saveReport() {
        this.setFailPercent(MathUtil.calculateFailPre(this.getTotalCount(), this.getFailCount()));
        this.setAvg(MathUtil.calculateAvgDura(this.getTotalCount(), this.getSum()));
        this.setQps(MathUtil.calculateQPS(this.getTotalCount() * 1000, this.getEndTime() - this.getStartTime()));
    }

    public String getKey(CalendarUtil calendar, Span span) {
        StringBuilder builder = new StringBuilder();
        builder.append(TimeStampUtil.timestampOfCurrentHour(calendar))
                .append("-").append(span.getAppName())
                .append("-").append("unknow");
        return builder.toString();
    }

    public String getPrevKey(CalendarUtil calendar, Span span) {
        StringBuilder builder = new StringBuilder();
        builder.append(TimeStampUtil.timestampOfPrevHour(calendar))
                .append("-").append(span.getAppName())
                .append("-").append("unknow");
        return builder.toString();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientApp) {
        this.clientName = clientApp;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverApp) {
        this.serverName = serverApp;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long count) {
        this.totalCount = count;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avgDura) {
        this.avg = avgDura;
    }

    public float getQps() {
        return qps;
    }

    public void setQps(float qps) {
        this.qps = qps;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public float getFailPercent() {
        return failPercent;
    }

    public void setFailPercent(float fialPercent) {
        this.failPercent = fialPercent;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return super.toString() + " DepenReport content [clientName=" + clientName + ", serverName=" + serverName + ", count=" + totalCount + ", " +
                "failCount=" + failCount + ", sum=" + sum + ", avg=" + avg + ", qps=" + qps + "]";
    }

}
