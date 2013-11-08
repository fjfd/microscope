=================================================================================================================
h_trace

CREATE EXTERNAL TABLE h_trace(key string, trace_id bigint, duration bigint, trace_name string, start_date bigint)      
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping" = ":key,cf:trace_id,cf:duration,cf:trace_name,cf:start_timestamp")
TBLPROPERTIES("hbase.table.name" = "trace");

==================================================================================================================
call time report

select trace_name, COUNT(duration) as dur_count 
from h_trace  
GROUP BY trace_name 
order by dur_count desc 
limit 2;

==================================================================================================================
duration report

select trace_name, trace_id, duration 
from h_trace 
order by duration desc 
limit 2;