

package com.anyilanxin.anyicloud.gateway;

import static com.anyilanxin.anyicloud.corecommon.constant.SysBaseConstant.BOOT_BASE_SCAN_PACKAGE;

import com.anyilanxin.anyicloud.corecommon.annotation.SkillfulCloudApplication;
import com.anyilanxin.anyicloud.corecommon.constant.TimeZoneConstant;

import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * start
 *
 * @author zxh
 * @date 2020-09-10 01:10
 * @since 1.0.0
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
