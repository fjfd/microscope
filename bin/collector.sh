#!/bin/sh

SERVER_PID=`ps auxf | grep "com.vipshop.microscope.collector.server.CollectorServer" | grep -v "grep"| awk '{print $2}'`
echo "news service interface server pid is ${SERVER_PID}"
if [ -n $SERVER_PID ] 
then
  kill $SERVER_PID
  echo "$SERVER_PID is killed!"
fi

SERVICE_HOME=/home/vipshop/platform/microscope/collector
cd ${SERVICE_HOME}
LIB_DIR=${SERVICE_HOME}/lib

LOGS_DIR=${SERVICE_HOME}/logs

ARCHIVE_SUFFIX=`date +%Y%m%d-%H%M`

$JAVA_ARGS  
="  
-server  
-Xmx3000M  
-Xms3000M  
-Xmn600M  
-XX:PermSize=500M  
-XX:MaxPermSize=500M  
-Xss256K  
-XX:+DisableExplicitGC  
-XX:SurvivorRatio=1 
-XX:+UseConcMarkSweepGC  
-XX:+UseParNewGC  
-XX:+CMSParallelRemarkEnabled  
-XX:+UseCMSCompactAtFullCollection  
-XX:CMSFullGCsBeforeCompaction=0 
-XX:+CMSClassUnloadingEnabled  
-XX:LargePageSizeInBytes=128M  
-XX:+UseFastAccessorMethods  
-XX:+UseCMSInitiatingOccupancyOnly  
-XX:CMSInitiatingOccupancyFraction=70 
-XX:SoftRefLRUPolicyMSPerMB=0 
-XX:+PrintClassHistogram  
-XX:+PrintGCDetails  
-XX:+PrintGCTimeStamps  
-XX:+PrintHeapAtGC  
-Xloggc:log/gc.log"; 


nohup java ${JAVA_ARGS} -jar microscope-collector-1.1.3.jar  & 1>${SERVICE_HOME}/logs/stdout.log 2>${SERVICE_HOME}/logs/stderr.log&

echo "news service starting..."