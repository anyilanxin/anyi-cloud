@echo off
title storage8090-%date%-%time%-%cd%
java -Dfile.encoding=utf-8 -jar -Xmx1024m -Xms1024m skillfull-storage-service-boot-1.0.0.jar ^
--server.port=8090  ^
--PROFILES_ACTIVE=test ^
--CONFIG_ENABLE=true ^
--NACOS_PASSWORD=skillfull ^
--NACOS_USERNAME=skillfull ^
--NACOS_ADDR=http://nacos:8848 ^
--NACOS_NAMESPACE=skillfull