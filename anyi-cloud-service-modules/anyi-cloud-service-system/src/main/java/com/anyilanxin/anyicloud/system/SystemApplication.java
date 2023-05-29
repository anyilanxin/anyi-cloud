

package com.anyilanxin.anyicloud.system;

import static com.anyilanxin.anyicloud.corecommon.constant.SysBaseConstant.BOOT_MAPPER_BASE_SCAN_PACKAGE;

import com.anyilanxin.anyicloud.corecommon.annotation.SkillfulCloudApplication;
import com.anyilanxin.anyicloud.corecommon.constant.TimeZoneConstant;

import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * start
 *
 * @author zxh
 * @date 2020-09-10 01:10
 * @since 1.0.0
 */
@Slf4j
@MapperScan(BOOT_MAPPER_BASE_SCAN_PACKAGE)
@EnableTransactionManagement
@SkillfulCloudApplication
public class SystemApplication {

    public static void main(String[] args) {
        // 设置时区避免容器时间不对
        TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneConstant.ASIA_SHANGHAI));
        SpringApplication.run(SystemApplication.class, args);
    }
}
