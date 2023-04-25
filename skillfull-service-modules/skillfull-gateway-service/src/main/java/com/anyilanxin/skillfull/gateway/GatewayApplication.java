package com.anyilanxin.skillfull.gateway;

import com.anyilanxin.skillfull.corecommon.annotation.SkillfulCloudApplication;
import com.anyilanxin.skillfull.corecommon.constant.TimeZoneConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

import static com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant.BOOT_BASE_SCAN_PACKAGE;

/**
 * start
 *
 * @author zxiaozhou
 * @date 2020-09-10 01:10
 * @since JDK11
 */
@Slf4j
@EnableAsync
@EnableFeignClients(basePackages = BOOT_BASE_SCAN_PACKAGE)
@SkillfulCloudApplication
public class GatewayApplication {
    public static void main(String[] args) {
        // 设置时区避免容器时间不对
        TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneConstant.ASIA_SHANGHAI));
        SpringApplication.run(GatewayApplication.class, args);
    }

}
