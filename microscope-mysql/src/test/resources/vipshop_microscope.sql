DROP TABLE IF EXISTS `msg_report`;

CREATE TABLE `msg_report` (
  `id` int(11) NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL COMMENT '年份',
  `month` tinyint(4) unsigned NOT NULL COMMENT '月份',
  `week` tinyint(4) unsigned NOT NULL COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL COMMENT '小时',
  `msg_num` bigint(20) unsigned NOT NULL COMMENT '消息数量',
  `msg_size` bigint(20) unsigned NOT NULL COMMENT '消息大小',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `source_report` */

DROP TABLE IF EXISTS `source_report`;

CREATE TABLE `source_report` (
  `id` int(11) NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL COMMENT '年份',
  `month` tinyint(4) unsigned NOT NULL COMMENT '月份',
  `week` tinyint(4) unsigned NOT NULL COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL COMMENT '小时',
  `app` varchar(50) NOT NULL COMMENT '应用名称',
  `name` varchar(50) NOT NULL COMMENT '二级分类',
  `server_type` varchar(20) NOT NULL COMMENT '服务类型',
  `server_ip` varchar(16) NOT NULL COMMENT '服务IP',
  `sql_type` varchar(10) NOT NULL COMMENT 'sql操作类型',
  `count` bigint(20) NOT NULL COMMENT '调用次数',
  `fail` bigint(20) NOT NULL COMMENT '失败次数',
  `failpre` float NOT NULL COMMENT '失败比例',
  `start_time` int(10) unsigned default NULL COMMENT '开始时间',
  `end_time` int(10) unsigned default NULL COMMENT '结束时间',
  `avg_dura` float unsigned NOT NULL COMMENT '平均时间',
  `tps` float unsigned NOT NULL COMMENT '每秒访问次数',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `trace_overtime_report` */

DROP TABLE IF EXISTS `trace_overtime_report`;

CREATE TABLE `trace_overtime_report` (
  `id` bigint(20) NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL COMMENT '年份',
  `month` tinyint(4) unsigned NOT NULL COMMENT '月份',
  `week` tinyint(4) unsigned NOT NULL COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL COMMENT '小时',
  `minute` tinyint(4) unsigned NOT NULL COMMENT '分钟',
  `app` varchar(50) NOT NULL COMMENT '应用名称',
  `ip_adress` varchar(16) NOT NULL COMMENT 'IP adress',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `avg_dura` float unsigned NOT NULL COMMENT '平均时间',
  `hit_count` bigint(20) unsigned NOT NULL COMMENT '请求次数',
  `fail_count` bigint(20) unsigned NOT NULL COMMENT '失败次数',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `trace_stat_report` */

DROP TABLE IF EXISTS `trace_stat_report`;

CREATE TABLE `trace_stat_report` (
  `id` bigint(20) NOT NULL auto_increment,
  `year` smallint(6) default NULL,
  `month` tinyint(4) default NULL,
  `week` tinyint(4) default NULL,
  `day` tinyint(4) default NULL,
  `hour` tinyint(4) default NULL,
  `app` varchar(50) default NULL COMMENT '应用',
  `ip_adress` varchar(16) default NULL COMMENT 'server IP',
  `type` varchar(20) default NULL COMMENT '一级类型',
  `name` varchar(50) default NULL COMMENT '二级名称',
  `total_count` bigint(20) default NULL COMMENT '总次数',
  `failure_count` bigint(20) default NULL COMMENT '失败次数',
  `failure_precent` float default NULL COMMENT '失败百分比',
  `min` int(11) default NULL COMMENT '最小时间',
  `max` int(11) default NULL COMMENT '最大时间',
  `sum` bigint(20) default NULL,
  `avg` float default NULL COMMENT '平均时间',
  `tps` float default NULL COMMENT '每秒发生次数',
  `start_time` int(10) unsigned default NULL,
  `end_time` int(10) unsigned default NULL,
  `region_0` int(11) default NULL,
  `region_1` int(11) default NULL,
  `region_2` int(11) default NULL,
  `region_3` int(11) default NULL,
  `region_4` int(11) default NULL,
  `region_5` int(11) default NULL,
  `region_6` int(11) default NULL,
  `region_7` int(11) default NULL,
  `region_8` int(11) default NULL,
  `region_9` int(11) default NULL,
  `region_10` int(11) default NULL,
  `region_11` int(11) default NULL,
  `region_12` int(11) default NULL,
  `region_13` int(11) default NULL,
  `region_14` int(11) default NULL,
  `region_15` int(11) default NULL,
  `region_16` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8;