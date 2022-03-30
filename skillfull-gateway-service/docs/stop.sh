#!/bin/bash

APP_NAME=`find -name  *.jar | xargs `
PID=`find -name  *.jar | xargs pgrep -f | xargs`
if [ "$PID" ]
then
  echo "---------->程序${APP_NAME}已经启动，正在杀掉"
  kill -9 $PID
  echo "---------->程序杀掉成功"
else
  echo "---------->程序${APP_NAME}没有启动，不需要执行关闭操作"
fi
