@echo off
title gateway1102-%date%-%time%-%cd%
java -Dfile.encoding=utf-8 -jar -Xmx1024m -Xms1024m anyi-gateway-service-boot-1.0.0.jar ^
--server.port=1102  ^
--PROFILES_ACTIVE=test ^
--CONFIG_ENABLE=true ^
--NACOS_PASSWORD=anyi ^
--NACOS_USERNAME=anyi ^
--NACOS_ADDR=http://nacos:8848 ^
--NACOS_NAMESPACE=anyi
