

package com.anyilanxin.anyicloud.monitor;

import com.anyilanxin.anyicloud.corecommon.annotation.SkillfulCloudApplication;
import com.anyilanxin.anyicloud.corecommon.constant.TimeZoneConstant;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * start
 *
 * @author zxh
 * @date 2020-09-10 01:10
 * @since 1.0.0
 */
@Slf4j
@EnableAdminServer
@SkillfulCloudApplication
public class MonitorApplication {

    public static void main(String[] args) {
        // 设置时区避免容器时间不对
        TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneConstant.ASIA_SHANGHAI));
        SpringApplication.run(MonitorApplication.class, args);
    }
}
