#!/bin/sh

JVM_OPTIONS="-server 
             -Xmx8000M 
			 -Xms8000M 
			 -Xmn3000M 
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
             -Xloggc:./log/gc.log"; 

nohup java $JVM_OPTIONS -jar microscope-test-1.1.3.jar &