// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.job;

import indi.zxiaozhou.skillfull.corecommon.annotation.SkillfulCloudApplication;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.BOOT_BASE_SCAN_PACKAGE;
import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.BOOT_MAPPER_BASE_SCAN_PACKAGE;

/**
 * start
 *
 * @author zxiaozhou
 * @date 2020-09-10 01:10
 * @since JDK11
 */
@Slf4j
@EnableAsync
@EnableCaching
@EnableFeignClients(basePackages = BOOT_BASE_SCAN_PACKAGE)
@MapperScan(BOOT_MAPPER_BASE_SCAN_PACKAGE)
@EnableTransactionManagement
@SkillfulCloudApplication
public class JobApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(JobApplication.class, args);
        CoreCommonUtils.printSysInfo(application);
    }
}
