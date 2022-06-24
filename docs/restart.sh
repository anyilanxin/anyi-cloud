#!/bin/bash
nohup java -jar \
-Dserver.port=8080 \
-DPROFILES_ACTIVE=test \
-DCONFIG_ENABLE=true \
-DNACOS_PASSWORD=skillfull \
-DNACOS_USERNAME=skillfull \
-DNACOS_ADDR=http://nacos:8848 \
-DNACOS_NAMESPACE=skillfull \
./*.jar -Xms1024m -Xmx1024m 服务>/dev/null 2>&1 &
