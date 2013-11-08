package com.vipshop.microscope.job.mapreduce;

import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.DependentColumnFilter;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;


public class MapReduce {
	private Configuration conf = new Configuration();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String zk_port;
	private String zk_quorum;
	private String range;
	private String h_master;
	private String db_user;
	private String db_pw;
	private String db_url;
	private String picket_top_size;
	private String remote_user;

	@SuppressWarnings("rawtypes")
	public void action(final Class<? extends TableMapper> mapperclass, final Class<? extends Reducer> reducerclass, final byte[] column) {
		UserGroupInformation ugi = UserGroupInformation.createRemoteUser(remote_user);
		ugi.doAs(new PrivilegedAction<Void>() {
			public Void run() {
				try {
					System.setProperty("path.separator", ":");

					conf.set("picket_range", range);
					conf.set("picket_top_size", picket_top_size);
					conf.set("startdate", sdf.format(new Date()));
					conf.set("hbase.zookeeper.property.clientPort", zk_port);
					conf.set("hbase.zookeeper.quorum", zk_quorum);
					conf.set("hbase.master", h_master);

					// conf.setBoolean("mapred.map.tasks.speculative.execution",
					// false);

					DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", db_url, db_user, db_pw);

					@SuppressWarnings("deprecation")
					Job job = new Job(conf);
					job.setJobName("Trace mapreduce job");
					// job.setJarByClass(DurTimeTask.class);
					job.setOutputFormatClass(DBOutputFormat.class);

					Scan scan = new Scan();
					DependentColumnFilter filter = new DependentColumnFilter(TableTrace.F_CFINFO_BYTE, column, false, CompareFilter.CompareOp.NO_OP, null);
					scan.setFilter(filter);
					if (Integer.parseInt(range) != 0) {
						long[] scanD = DateUtil.getTime(Integer.parseInt(range));
						scan.setTimeRange(scanD[0], scanD[1]);
					}
					// 本次mr任务scan的所有数据不放在缓存中
					scan.setCacheBlocks(false);

					//
					if (new String(column).equalsIgnoreCase("duration")) {
						TableMapReduceUtil.initTableMapperJob("trace", scan, mapperclass, Text.class, IntWritable.class, job);
					} else {
						TableMapReduceUtil.initTableMapperJob("trace", scan, mapperclass, Text.class, Text.class, job);
					}

					job.setReducerClass(reducerclass); // reducer class
					job.setNumReduceTasks(1); // at least one, adjust as
												// required

					if (new String(column).equalsIgnoreCase("duration")) {
						DBOutputFormat.setOutput(job, TableTrace.TABLE_TRACE, new String[] { "type", "position", "trace_id", "operation_date", "range", "start_date" });
					} else {
						DBOutputFormat.setOutput(job, TableTrace.TABLE_TRACE, new String[] { "type", "position", "trace_name", "operation_date", "range", "start_date" });
					}

					job.waitForCompletion(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf) {
		MapReduce.sdf = sdf;
	}

	public String getRemote_user() {
		return remote_user;
	}

	public void setRemote_user(String remote_user) {
		this.remote_user = remote_user;
	}

	public String getZk_port() {
		return zk_port;
	}

	public void setZk_port(String zk_port) {
		this.zk_port = zk_port;
	}

	public String getZk_quorum() {
		return zk_quorum;
	}

	public void setZk_quorum(String zk_quorum) {
		this.zk_quorum = zk_quorum;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getH_master() {
		return h_master;
	}

	public void setH_master(String h_master) {
		this.h_master = h_master;
	}

	public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getDb_pw() {
		return db_pw;
	}

	public void setDb_pw(String db_pw) {
		this.db_pw = db_pw;
	}

	public String getDb_url() {
		return db_url;
	}

	public String getPicket_top_size() {
		return picket_top_size;
	}

	public void setPicket_top_size(String picket_top_size) {
		this.picket_top_size = picket_top_size;
	}

	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}

}