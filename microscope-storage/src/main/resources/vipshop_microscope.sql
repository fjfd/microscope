CREATE DATABASE IF NOT EXISTS vipshop_microscope DEFAULT CHARACTER SET utf8;

USE vipshop_microscope;

DROP TABLE IF EXISTS `depen_report`;
DROP TABLE IF EXISTS `msg_report`;
DROP TABLE IF EXISTS `problem_report`;
DROP TABLE IF EXISTS `source_report`;
DROP TABLE IF EXISTS `top_report`;
DROP TABLE IF EXISTS `trace_overtime_report`;
DROP TABLE IF EXISTS `trace_report`;

CREATE TABLE `depen_report` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL default '0' COMMENT '年',
  `month` tinyint(4) unsigned NOT NULL default '0' COMMENT '月',
  `week` tinyint(4) unsigned NOT NULL default '0' COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL default '0' COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL default '0' COMMENT '小时',
  `client_name` varchar(50) NOT NULL default '',
  `server_name` varchar(50) NOT NULL default '',
  `total_count` bigint(20) NOT NULL default '0',
  `fail_count` bigint(20) NOT NULL default '0',
  `fail_percent` float NOT NULL default '0',
  `avg` float NOT NULL default '0',
  `qps` float NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `msg_report` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL default '0' COMMENT '年',
  `month` tinyint(4) unsigned NOT NULL default '0' COMMENT '月',
  `week` tinyint(4) unsigned NOT NULL default '0' COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL default '0' COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL default '0' COMMENT '小时',
  `msg_num` bigint(20) unsigned NOT NULL default '0' COMMENT '消息数量',
  `msg_size` bigint(20) unsigned NOT NULL default '0' COMMENT '消息大小',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `problem_report` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL default '0' COMMENT '年',
  `month` tinyint(4) unsigned NOT NULL default '0' COMMENT '月',
  `week` tinyint(4) unsigned NOT NULL default '0' COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL default '0' COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL default '0' COMMENT '小时',
  `app_name` varchar(50) NOT NULL default '' COMMENT 'app名称',
  `app_ip` int(11) unsigned NOT NULL default '0' COMMENT '应用Ip',
  `pro_type` tinyint(4) NOT NULL default '0' COMMENT '问题类型',
  `pro_time` tinyint(4) NOT NULL default '0' COMMENT '所属时间区域',
  `pro_count` int(11) NOT NULL default '0',
  `pro_desc` varchar(255) NOT NULL default '' COMMENT '问题描述',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

CREATE TABLE `source_report` (
  `id` int(11) NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL default '0' COMMENT '年',
  `month` tinyint(4) unsigned NOT NULL default '0' COMMENT '月份',
  `week` tinyint(4) unsigned NOT NULL default '0' COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL default '0' COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL default '0' COMMENT '小时',
  `app_name` varchar(50) NOT NULL default '' COMMENT '应用名称',
  `app_ip` int(11) unsigned NOT NULL default '0' COMMENT '应用Ip',
  `server_name` varchar(20) NOT NULL default '' COMMENT '服务类型',
  `sql_type` varchar(10) NOT NULL default '' COMMENT 'sql操作类型',
  `total_count` bigint(20) unsigned NOT NULL default '0' COMMENT '调用次数',
  `fail_count` bigint(20) unsigned NOT NULL default '0' COMMENT '失败次数',
  `fail_percent` float unsigned NOT NULL default '0' COMMENT '失败比例',
  `avg` float unsigned NOT NULL default '0' COMMENT '平均时间',
  `qps` float unsigned NOT NULL default '0' COMMENT '每秒访问次数',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

CREATE TABLE `top_report` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL default '0',
  `month` tinyint(4) unsigned NOT NULL default '0',
  `week` tinyint(4) unsigned NOT NULL default '0',
  `day` tinyint(4) unsigned NOT NULL default '0',
  `hour` tinyint(4) unsigned NOT NULL default '0',
  `minute` tinyint(4) unsigned NOT NULL default '0',
  `top_type` tinyint(4) unsigned NOT NULL default '0',
  `top_1_name` varchar(50) default '',
  `top_1_data` int(11) unsigned default '0',
  `top_2_name` varchar(50) default '',
  `top_2_data` int(11) unsigned default '0',
  `top_3_name` varchar(50) default '',
  `top_3_data` int(11) unsigned default '0',
  `top_4_name` varchar(50) default '',
  `top_4_data` int(11) unsigned default '0',
  `top_5_name` varchar(50) default '',
  `top_5_data` int(11) unsigned default '0',
  `top_6_name` varchar(50) default '',
  `top_6_data` int(11) unsigned default '0',
  `top_7_name` varchar(50) default '',
  `top_7_data` int(11) unsigned default '0',
  `top_8_name` varchar(50) default '',
  `top_8_data` int(11) unsigned default '0',
  `top_9_name` varchar(50) default '',
  `top_9_data` int(11) unsigned default '0',
  `top_10_name` varchar(50) default '',
  `top_10_data` int(11) unsigned default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

CREATE TABLE `trace_overtime_report` (
  `id` bigint(20) NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL default '0' COMMENT '年',
  `month` tinyint(4) unsigned NOT NULL default '0' COMMENT '月份',
  `week` tinyint(4) unsigned NOT NULL default '0' COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL default '0' COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL default '0' COMMENT '小时',
  `minute` tinyint(4) unsigned NOT NULL default '0' COMMENT '分钟',
  `app_name` varchar(50) NOT NULL default '' COMMENT '应用name',
  `app_ip` int(11) unsigned NOT NULL default '0' COMMENT '应用Ip',
  `type` tinyint(4) NOT NULL default '0' COMMENT '一级类型',
  `name` varchar(50) NOT NULL default '' COMMENT '名称',
  `avg` float unsigned NOT NULL default '0' COMMENT '平均时间',
  `hit` bigint(20) unsigned NOT NULL default '0' COMMENT '请求次数',
  `fail` bigint(20) unsigned NOT NULL default '0' COMMENT '失败次数',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=612 DEFAULT CHARSET=utf8;

CREATE TABLE `trace_report` (
  `id` bigint(20) NOT NULL auto_increment,
  `year` smallint(6) unsigned NOT NULL default '0' COMMENT '年',
  `month` tinyint(4) unsigned NOT NULL default '0' COMMENT '月份',
  `week` tinyint(4) unsigned NOT NULL default '0' COMMENT '周',
  `day` tinyint(4) unsigned NOT NULL default '0' COMMENT '日',
  `hour` tinyint(4) unsigned NOT NULL default '0' COMMENT '小时',
  `app_name` varchar(50) NOT NULL default '' COMMENT '应用name',
  `app_ip` int(11) unsigned NOT NULL default '0' COMMENT '应用Ip',
  `type` tinyint(4) NOT NULL default '0' COMMENT '一级类型',
  `name` varchar(50) NOT NULL default '' COMMENT '二级名称',
  `total_count` bigint(20) unsigned NOT NULL default '0' COMMENT '总次数',
  `fail_count` bigint(20) unsigned NOT NULL default '0' COMMENT '失败次数',
  `fail_percent` float unsigned NOT NULL default '0' COMMENT '失败百分比',
  `min` int(11) unsigned NOT NULL default '0' COMMENT '最小时间',
  `max` int(11) unsigned NOT NULL default '0' COMMENT '最大时间',
  `avg` float unsigned NOT NULL default '0' COMMENT '平均时间',
  `qps` float unsigned NOT NULL default '0' COMMENT '每秒发生次数',
  `region_0` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：0~1 ms',
  `region_1` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：1~2 ms',
  `region_2` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：2~4 ms',
  `region_3` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：4~8 ms',
  `region_4` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：8~16 ms',
  `region_5` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：16~32 ms',
  `region_6` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：32~64 ms',
  `region_7` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：64~128 ms',
  `region_8` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：128~256 ms',
  `region_9` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：256~512 ms',
  `region_10` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：512~1024 ms',
  `region_11` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：1024~2024 ms',
  `region_12` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：2048~4096 ms',
  `region_13` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：4096~8192 ms',
  `region_14` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：8192~16384 ms',
  `region_15` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：16384~32768 ms',
  `region_16` int(11) unsigned NOT NULL default '0' COMMENT '请求时间：32768~65536 ms',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
