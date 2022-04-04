#!/bin/bash

APP_NAME=`find -name  *.jar | xargs `
PID=`find -name  *.jar | xargs pgrep -f | xargs`
if [ "$PID" ]
then
  echo "---------->程序${APP_NAME}已经启动，正在杀掉"
  kill -9 $PID
  echo "---------->程序杀掉成功"
fi
echo "---------->程序正在启动"
nohup java -jar \
-Dserver.port=8084 \
-DPROFILES_ACTIVE=test \
-DCONFIG_ENABLE=true \
-DNACOS_PASSWORD=skillfull \
-DNACOS_USERNAME=skillfull \
-DNACOS_ADDR=http://nacos:8848 \
-DNACOS_NAMESPACE=skillfull \
./*.jar -Xms1024m -Xmx1024m 日志服务>/dev/null 2>&1 &
