CREATE TABLE `trace_stat` (
  `trace_name` varchar(20) NOT NULL,
  `total_count` bigint(20) DEFAULT NULL,
  `failure_count` bigint(20) DEFAULT NULL,
  `failure_precent` int(11) DEFAULT NULL,
  `min` int(11) DEFAULT NULL,
  `max` int(11) DEFAULT NULL,
  `avg` int(11) DEFAULT NULL,
  PRIMARY KEY (`trace_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

