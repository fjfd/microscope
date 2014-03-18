#! /bin/bash

source /etc/profile
lockfile=/home/vipshop/platform/microscope.lock
function restart()
{
  ps -ef | grep microscope-query-  | grep -v grep  | awk '{print $2}'| xargs kill
  sleep 5
  still=`ps -ef  | grep -v grep | grep -c microscope-query-`
  if [ $still -ne 0 ];then
        echo "[WARN]$(date) microscope-query is still running after killed 5 seconds. kill -9 is running"
        ps -ef | grep microscope-query-  | grep -v grep  | awk '{print $2}'| xargs kill -9
        rm -f ${lockfile}
  fi
  echo "[INFO]$(date) starting microscope-query"
  mv nohup.out nohup.out.$(date +"%Y%m%d-%H%M").log 
  nohup java -server -Xmx1000M -Xms1000M -Xmn500M -XX:PermSize=128M -XX:MaxPermSize=128M -Xss256K -XX:SurvivorRatio=8 -XX:+UseCompressedOops -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSParallelRemarkEnabled -XX:+DisableExplicitGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:SoftRefLRUPolicyMSPerMB=0 -Xnoclassgc -Xloggc:query-$(date +%Y%m%d-%H%M%S).log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar microscope-query-1.3.1.jar  &
}

if [ ! -e $lockfile ];then
  trap "rm -f $lockfile; exit" INT TERM EXIT
  touch $lockfile
  restart
  rm -f ${lockfile}
else
  echo "[WARN]$(date) microscope query is running ..." 
fi
