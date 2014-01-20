#!/bin/sh

program="collector"    

sn=`ps -ef | grep $program | grep -v grep |awk '{print $2}'`  
if [ "${sn}" != "" ]    
then
kill -9 $sn

JVM_OPTIONS="-server 
             -Xmx8000M 
			 -Xms8000M 
			 -Xmn3000M 
			 -XX:PermSize=500M 
			 -XX:MaxPermSize=500M 
			 -Xss256K 
			 -XX:+UseCompressedOops
			 -XX:+DisableExplicitGC  
			 -XX:SurvivorRatio=8 
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
			 -XX:+HeapDumpOnOutOfMemoryError 
			 -XX:HeapDumpPath=./
			 -XX:+PrintClassHistogram  
			 -XX:+PrintGCDetails  
			 -XX:+PrintGCTimeStamps  
			 -XX:+PrintHeapAtGC  
			 -XX:+PrintGCApplicationConcurrentTime
			 -XX:+PrintGCApplicationStoppedTime
             -Xloggc:./log/gc.log"; 

nohup java -Dcom.sun.management.jmxremote.port="1088" 
		   -Dcom.sun.management.jmxremote.ssl=false 
		   -Dcom.sun.management.jmxremote.authenticate=false 
		   $JVM_OPTIONS 
		   -jar microscope-collector-1.1.3.jar &

echo running collector service ...